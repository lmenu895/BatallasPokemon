package ar.edu.unlam.tallerweb1.controladores;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.unlam.tallerweb1.modelo.Billetera;
import ar.edu.unlam.tallerweb1.modelo.Usuario;
import ar.edu.unlam.tallerweb1.servicios.ServicioBilletera;
import ar.edu.unlam.tallerweb1.servicios.ServicioUsuario;

@Controller
public class ControladorBilletera {

	private ServicioBilletera servicioBilletera;
	private ServicioUsuario servicioUsuario;
	
	@Autowired
	public ControladorBilletera(ServicioBilletera servicioBilletera, ServicioUsuario servicioUsuario) {
		
		this.servicioBilletera = servicioBilletera;
		this.servicioUsuario = servicioUsuario;
	}
	
	@RequestMapping(path="/registroBilletera",  method=RequestMethod.GET)
	public ModelAndView registro(HttpServletRequest request) {

		ModelMap modelo = new ModelMap();
		Billetera billetera = new Billetera();
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		if(usuario != null) {
			modelo.addAttribute("usuario", usuario);
			billetera.setUsuario(usuario);
			modelo.put("billetera", billetera);
			
			return new ModelAndView("registroBilletera", modelo);
		}
		return new ModelAndView("redirect:/login");
	}
	
	@RequestMapping(path="/procesarRegistroBilletera", method=RequestMethod.GET)
	public ModelAndView procesarRegistro(@ModelAttribute("billetera") Billetera billetera, HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		Billetera billeteraEncontrada = servicioBilletera.consultarBilleteraDeUsuario(usuario.getId());
		try {
			if(usuario != null) {
				if(billeteraEncontrada == null) {
					billetera.setSaldo(0.0);
					billetera.setUsuario(usuario);
					servicioBilletera.registrarBilletera(billetera);
					modelo.put("usuario", usuario);
					modelo.put("billetera", billetera);
					modelo.put("saldo", billetera.getSaldo());
					
					return new ModelAndView("confirmacionBilletera", modelo);
			}else {
				
				return new ModelAndView("redirect:/home");
			}
				
		}else {
			
			return new ModelAndView("redirect:/login");
		}
		
		}catch(Exception e) {
			
			modelo.put("billetera", billetera);
			modelo.put("error", e.getMessage());
			
		}
		
		return new ModelAndView("registroBilletera", modelo);
		
	}
	
	
//	@RequestMapping(path= "/mostrarBilletera", method=RequestMethod.GET)
//	 public ModelAndView mostrarBilletera(HttpServletRequest request) {
//        ModelMap modelo = new ModelMap();
//        Long id = (Long) request.getSession().getAttribute("id");
//        Usuario usuario = servicioUsuario.buscarUsuario(id);
//        Billetera billetera = servicioBilletera.consultarBilleteraDeUsuario(usuario);
//
//        try {
//            if (usuario != null) {
//                if (billetera != null) {
//                    modelo.put("saldo", billetera.getSaldo());
//                    modelo.put("usuario", usuario);
//                    return new ModelAndView("mostrarBilletera",modelo);
//                } else {
//                	modelo.put("error", "Usted no posee una billetera. Por favor, genere una");
//                	return new ModelAndView("registroBilletera",modelo);
//                }
//            } else {
//            	return new ModelAndView("redirect:/login");
//            }
//        } catch (Exception e) {
//        	modelo.put("billetera", billetera);
//        	modelo.put("error", e.getMessage());
//        	return new ModelAndView("error",modelo);
//        }
//	}

     
	
	@RequestMapping(path="/formularioSaldo", method=RequestMethod.GET)
	public ModelAndView formularioSaldo(@ModelAttribute("billetera") Billetera billetera, HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		billetera = servicioBilletera.consultarBilleteraDeUsuario(usuario.getId());
		
		try{
			if(usuario != null) {
				if(billetera != null){
					modelo.put("usuario", usuario);
					return new ModelAndView("ingresarSaldo", modelo);	
				}
			}else {
				
				return new ModelAndView("redirect:/login");
			}
		
			
		} catch(Exception e) {
			
			modelo.put("usuario", usuario);
			modelo.put("error", e.getMessage());
		}
			return new ModelAndView("redirect:/formularioSaldo");
	}
	
	@RequestMapping(path="/procesarSaldo", method=RequestMethod.POST)
	public ModelAndView ingresarSaldo(@ModelAttribute("billetera") Billetera billetera,
									@RequestParam("monto") Double monto, HttpServletRequest request
									) {
		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		billetera = servicioBilletera.consultarBilleteraDeUsuario(usuario.getId());
		
		try {
			if(usuario != null) {
				if(billetera != null) {
					if(monto >= 50) {
						servicioBilletera.ingresarSaldo(billetera, monto);
						modelo.put("billetera", billetera);
						modelo.put("usuario", usuario);
						modelo.put("saldo", billetera.getSaldo());
						
						return new ModelAndView("redirect:/dineroCargadoExitosamente");
					}else {
						
						modelo.put("usuario", usuario);
						modelo.put("billetera", billetera);
						modelo.put("error", "Ingrese un monto mayor a $50");
						return new ModelAndView("ingresarSaldo", modelo);
					}					
				}
				
			}
			
		} catch(Exception e) {

			modelo.put("exception", e.getMessage());
			modelo.put("error", "Ingrese un monto");
			modelo.put("billetera", billetera);
			modelo.put("usuario", usuario);
			return new ModelAndView("ingresarSaldo", modelo);
		}
		
		return new ModelAndView("redirect:/login"); 
	}
	
	@RequestMapping(path="/dineroCargadoExitosamente", method= RequestMethod.GET)
	public ModelAndView mostrarVistaDeExito(HttpServletRequest request) {
		ModelMap modelo = new ModelMap();
		Long id = (Long) request.getSession().getAttribute("id");
		Usuario usuario = servicioUsuario.buscar(id);
		Billetera billetera = servicioBilletera.consultarBilleteraDeUsuario(usuario.getId());
		
		if(usuario != null) {
		modelo.put("billetera", billetera);
		modelo.put("usuario", usuario);
		modelo.put("saldo", billetera.getSaldo());
		
		return new ModelAndView("confirmacionSaldo", modelo);
		
		}
		
		return new ModelAndView("redirect:/login");
	}
}
	
