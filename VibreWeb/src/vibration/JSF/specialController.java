package vibration.JSF;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import vibration.EJB.LogincaseLocal;
import vibration.EJB.UserManagementEJBLocal;
import vibration.JPA.Personen;

@Named("specialController")
@Stateless
public class specialController implements Serializable {

	private static final long serialVersionUID = 5192253043337737431L;

	@EJB
	private UserManagementEJBLocal userEJB;

	@EJB
	private LogincaseLocal loginEJB;

	private HashMap<String, Runnable> redirectionMap = new HashMap<String, Runnable>();
	private String url;
	private Personen gebruiker = new Personen();

	@PostConstruct
	public void init() {
		url = "";
		redirectionMap.put("home", () -> homePage());
		redirectionMap.put("proj", () -> projectZoek());
		redirectionMap.put("user", () -> userZoek());
		redirectionMap.put("login", () -> loginPage());
		redirectionMap.put("register", () -> registerPage());
		redirectionMap.put("userpage", () -> userPage());
		redirectionMap.put("", () -> errorPage());

	}

	public void accesCheck() {
		System.out.println("activated "+ url);
		if (gebruiker.getIdpersonen() == 0) {
			redirectionMap.get(url).run();
		}

	}

	public void homePage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void projectZoek() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/projectenZoekpagina.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void userZoek() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/userZoekPagina.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void registerPage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/register.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void errorPage() {
		try {
			FacesContext.getCurrentInstance().getExternalContext()
					.redirect("/VibreWeb/error/onbestaandePaginaError.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void userPage() {
		try {
			if (!loginEJB.loginCheck()) {
				FacesContext.getCurrentInstance().getExternalContext()
						.redirect("/VibreWeb/spotter/profielpagina.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/login.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void loginPage() {
		try {
			if (!loginEJB.loginCheck()) {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/re-login.xhtml");
			} else {
				FacesContext.getCurrentInstance().getExternalContext().redirect("/VibreWeb/login.xhtml");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public HashMap<String, Runnable> getRedirectionMap() {
		return redirectionMap;
	}

	public void setRedirectionMap(HashMap<String, Runnable> redirectionMap) {
		this.redirectionMap = redirectionMap;
	}

	public String getUrl() {
		return url;
	}
	public void urlSet(){
		System.out.println("urlSet actief");
		FacesContext context = FacesContext.getCurrentInstance();
		url = context.getExternalContext().getRequestParameterMap().get("urlForm:url");
		System.out.println(url);
	}
	public void setUrl(String url) {
	
		this.url = url;
	}

	public Personen getGebruiker() {
		return gebruiker;
	}

	public void setGebruiker(Personen gebruiker) {
		this.gebruiker = gebruiker;
	}

}
