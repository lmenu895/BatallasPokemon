package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorAdmin {

	@RequestMapping("/admin")
	public ModelAndView irAAdmin(HttpServletRequest request) {

		if (request.getSession().getAttribute("usuario") == null
				|| !(Boolean) request.getSession().getAttribute("esAdmin"))
			return new ModelAndView("redirect:/home");
		return new ModelAndView("admin");
	}
}
