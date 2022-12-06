
package es.uc3m.tiw.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import es.uc3m.tiw.domains.User;
import es.uc3m.tiw.domains.Address;

@Controller
public class Consumer {

	@Autowired
	RestTemplate restTemplate;
	
	// NAVEGACION 
	@RequestMapping("/")	
	public String index(){
		return "index";
	}
	
	@RequestMapping (value = "pagina-crear-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioDelUsuario(Model modelo){
		modelo.addAttribute("usuario", new User());
		return "ViewCrearUsuario";
	}

	@RequestMapping (value = "pagina-borrar-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioBorrarUsuario(){
		return "ViewDeleteUsuario.html";
	}

	@RequestMapping (value = "pagina-update-usuario", method = RequestMethod.GET)
	public String mostrarElFormularioUpdateUsuario(Model modelo){
		modelo.addAttribute("usuario", new User());
		return "ViewUpdateUsuario.html";
	}


	// LLAMADAS AL CONTROLADOR
	@RequestMapping (value = "pagina-usuario/{name}", method = RequestMethod.GET)
	public String returnUsuarios(Model model, @PathVariable String name) {
		
		User us = restTemplate.getForObject("http://localhost:8082/users/{name}", User.class, name);
		model.addAttribute("usuario", us);
		return "viewUsuarios";
		
	}
	
	@RequestMapping (value = "pagina-todos-usuarios", method = RequestMethod.GET)
	public String returnTodosUsuarios(Model model) {
		User[] listaUs = restTemplate.getForObject("http://localhost:8082/users", User[].class);
		model.addAttribute("userList", listaUs);
		return "viewTodosUsuarios";
	}
	
	
	@RequestMapping (value = "pagina-post-usuario", method = RequestMethod.POST)
	public String saveUser(Model model, @ModelAttribute User us) {
		User newUser = restTemplate.postForObject("http://localhost:8082/users", us, User.class);
		model.addAttribute("usuario", newUser);
		return "viewUsuarios";
	}
	
	@RequestMapping (value = "pagina-delete-usuario", method = RequestMethod.POST)
	public String deleteUser(Model model, @RequestParam String username){
		User delUser = restTemplate.getForObject("http://localhost:8082/users/{username}", User.class, username);
		if (delUser != null) {
			restTemplate.delete("http://localhost:8082/users/{id}", delUser.getIduser());
		}
		return "index";	
	}

	@RequestMapping (value = "pagina-search-usuario", method = RequestMethod.POST)
	public String searchUsuarios(Model model, @RequestParam String username) {
		User us = restTemplate.getForObject("http://localhost:8082/users/{username}", User.class, username);
		model.addAttribute("usuario", us);
		return "viewUpdateUsuario";
		
	}
	
	@RequestMapping (value = "pagina-update-usuario", method = RequestMethod.POST)
	public String updateUser(Model model, @ModelAttribute User us){
		Long id = us.getIduser();
		restTemplate.put("http://localhost:8082/users/" + id, us, User.class);
		return "index";	
	}
	

	
			
}
