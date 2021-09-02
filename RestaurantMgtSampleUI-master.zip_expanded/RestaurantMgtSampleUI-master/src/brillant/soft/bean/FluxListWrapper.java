/**
 * 
 */
package brillant.soft.bean;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author bk
 *
 */
@XmlRootElement(name="fluxs")
public class FluxListWrapper {

	private List<Flux> fluxs;
	
	@XmlElement(name="flux")
	public List<Flux> getFlux(){
		return fluxs;
	}
	
	public void setFlux(List<Flux> f) {
		this.fluxs = f;
	}
	
}
