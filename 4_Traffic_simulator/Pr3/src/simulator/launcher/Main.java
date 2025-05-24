package simulator.launcher;

import java.io.*;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import com.formdev.flatlaf.FlatIntelliJLaf;

import simulator.control.Controller;
import simulator.factories.*;
import simulator.model.*;
import simulator.view.MainWindow;

import java.util.*;

import javax.swing.SwingUtilities;

public class Main {

	private static String _inFile = null;
	private static String _outFile = null;
	private static int _timeLimit;
	private static Factory<Event> _eventsFactory = null;
	private static boolean gui;

	private static void parseArgs(String[] args) {

		// define the valid command line options
		//
		Options cmdLineOptions = buildOptions();

		// parse the command line as provided in args
		//
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine line = parser.parse(cmdLineOptions, args);
			parseModeOption(line);
			parseHelpOption(line, cmdLineOptions);
			parseInFileOption(line);
			parseOutFileOption(line);
			parseTicksOption(line);

			// if there are some remaining arguments, then something wrong is
			// provided in the command line!
			//
			String[] remaining = line.getArgs();
			if (remaining.length > 0) {
				String error = "Illegal arguments:";
				for (String o : remaining)
					error += (" " + o);
				throw new ParseException(error);
			}

		} catch (ParseException e) {
			System.err.println(e.getLocalizedMessage());
			System.exit(1);
		}

	}

	private static Options buildOptions() {
		Options cmdLineOptions = new Options();

		cmdLineOptions.addOption(Option.builder("i").longOpt("input").hasArg().desc("Events input file.").build());
		cmdLineOptions.addOption(
				Option.builder("o").longOpt("output").hasArg().desc("Output file, where reports are written.").build());
		cmdLineOptions.addOption(Option.builder("h").longOpt("help").desc("Print this message.").build());
		cmdLineOptions.addOption(Option.builder("t").longOpt("ticks").hasArg().desc("Ticks to the simulator's main loop (default value is 10).").build());
		cmdLineOptions.addOption(Option.builder("m").longOpt("mode").hasArg().desc("Run mode of the simulator").build());
		
		return cmdLineOptions;
	}

	private static void parseHelpOption(CommandLine line, Options cmdLineOptions) {
		if (line.hasOption("h")) {
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp(Main.class.getCanonicalName(), cmdLineOptions, true);
			System.exit(0);
		}
	}

	private static void parseInFileOption(CommandLine line) throws ParseException {
		_inFile = line.getOptionValue("i");
		if (_inFile == null && !gui) {
			throw new ParseException("An events file is missing");
		}
	}

	private static void parseOutFileOption(CommandLine line) throws ParseException {
		if (!gui) _outFile = line.getOptionValue("o");
	}
	
	private static void parseTicksOption(CommandLine line) throws ParseException {
		if(!gui) {
			try {
				_timeLimit = Integer.valueOf(line.getOptionValue("t"));
			}
			catch(NumberFormatException e) {
				if (line.getOptionValue("t") == null) {
					_timeLimit = 10;
				}
				else throw new ParseException("The number of ticks is not a number");
			}
			
			if (_timeLimit <= 0) _timeLimit = 10;
		}
	}
	
	private static void parseModeOption(CommandLine line) {
		gui = line.getOptionValue("m") != "console";
	}


	private static void initFactories() {
		List<Builder<LightSwitchingStrategy>> lsbs = new ArrayList<>();
		lsbs.add(new RoundRobinStrategyBuilder());
		lsbs.add(new MostCrowdedStrategyBuilder());
		Factory<LightSwitchingStrategy> lssFactory = new BuilderBasedFactory<>(lsbs);
		
		List<Builder<DequeuingStrategy>> dqbs = new ArrayList<>();
		dqbs.add(new MoveFirstStrategyBuilder());
		dqbs.add(new MoveAllStrategyBuilder());
		Factory<DequeuingStrategy> dqsFactory = new BuilderBasedFactory<>(dqbs);

		List<Builder<Event>> ebs = new ArrayList<>();
		ebs.add(new NewJunctionEventBuilder(lssFactory, dqsFactory));
		ebs.add(new NewCityRoadEventBuilder());
		ebs.add(new NewInterCityRoadEventBuilder());
		ebs.add(new NewVehicleEventBuilder());
		ebs.add(new SetWeatherEventBuilder());
		ebs.add(new SetContClassEventBuilder());
		_eventsFactory = new BuilderBasedFactory<>(ebs);
	}
	
	private static void startGUIMode() throws IOException {
		TrafficSimulator ts = new TrafficSimulator();
		Controller c = new Controller (ts, _eventsFactory);
		
		if (_inFile != null) {
			InputStream in = new FileInputStream(_inFile);
			c.loadEvents(in);
			in.close();
		}
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new MainWindow(c);
			}
		});
	}
	
	private static void startBatchMode() throws IOException {
		
		InputStream in = new FileInputStream(_inFile);
		
		OutputStream out;
		if (_outFile != null) out = new FileOutputStream (_outFile);
		else out = System.out;
	
		TrafficSimulator ts = new TrafficSimulator();
		Controller c = new Controller (ts, _eventsFactory);
		c.loadEvents(in);
		in.close();
		
		c.run(_timeLimit, out);
		out.close();
	}

	private static void start(String[] args) throws IOException {
		initFactories();
		parseArgs(args);
		if (gui) {
			Locale.setDefault(Locale.ENGLISH);
			FlatIntelliJLaf.setup();
			startGUIMode();
		}
		else startBatchMode();
	}

	// example command lines:
	//
	// -i resources/examples/ex1.json
	// -i resources/examples/ex1.json -t 300
	// -i resources/examples/ex1.json -o resources/tmp/ex1.out.json
	// --help

	public static void main(String[] args) {
		try {
			start(args);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
