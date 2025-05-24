package tp1.logic.lemmingsRole;

import java.util.Arrays;
import java.util.List;

import tp1.view.Messages;


public class LemmingRoleFactory {
	private static final List<LemmingRole> available_roles = Arrays.asList(
			new DownCaverRole(),
			new ParachuterRole(),
			new WalkerRole()
			);
	
	
	public static LemmingRole parse(String input)  {
		for (LemmingRole r: available_roles) {
			if (r.parse(input) != null)
				return r;
		}
		return null;
	}

	public static String helpText() {
		StringBuilder s = new StringBuilder();
		for (LemmingRole r: available_roles) {
			s.append(Messages.LINE_2TABS.formatted(r.getHelp()));
		}
		return s.toString();
	}
}
