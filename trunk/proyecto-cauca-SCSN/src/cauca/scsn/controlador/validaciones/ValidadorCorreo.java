package cauca.scsn.controlador.validaciones;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import cauca.scsn.controlador.ControladorMensajes;

@FacesValidator("validadorCorreo")
public class ValidadorCorreo implements Validator {

	@Override
	public void validate(FacesContext facesContext, UIComponent uiComponent, Object valor)
			throws ValidatorException {
		Pattern patron = Pattern.compile("\\w+@\\w+\\.\\w+");
		Matcher matcher = patron.matcher((CharSequence) valor);
		HtmlInputText htmlInputText = (HtmlInputText) uiComponent;
		
		String etiqueta;
		
		if (htmlInputText.getLabel() == null || htmlInputText.getLabel().trim().equals("")) {
			etiqueta = htmlInputText.getId();
		} else {
			etiqueta = htmlInputText.getLabel();
		}
		
		if (!matcher.matches()) {
			new ValidatorException(new ControladorMensajes().validacion(etiqueta + ": No es un e-mail válido"));
		}
	}
}
