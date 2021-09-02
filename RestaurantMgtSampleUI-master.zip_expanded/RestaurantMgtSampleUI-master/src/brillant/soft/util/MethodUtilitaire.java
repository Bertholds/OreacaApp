/**
 * 
 */
package brillant.soft.util;

import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

/**
 * @author bk
 *
 */
public class MethodUtilitaire {
	public static void saveAlert(Object object, String title, String content) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(content);
		alert.showAndWait();
	}

	public static void deleteNoPersonSelectedAlert(String title, String header, String content) {
		// Nothing selected.
		Alert alert = new Alert(AlertType.WARNING);
		// alert.initOwner( );
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}

	public static boolean confirmationDialog(Object object, String title, String header, String content) {

		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		ButtonType cancel = new ButtonType("Cancel");
		ButtonType yes = new ButtonType("Yes");
		alert.getButtonTypes().clear();
		alert.getButtonTypes().addAll(cancel, yes);
		Optional<ButtonType> optional = alert.showAndWait();
		if (optional.get() == yes) {
			return true;
		} else
			return false;
	}

	public static void errorMessageAlert(String title, String header, String content) {
		// Nothing selected.
		Alert alert = new Alert(AlertType.ERROR);
		// alert.initOwner( );
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);

		alert.showAndWait();
	}

}
