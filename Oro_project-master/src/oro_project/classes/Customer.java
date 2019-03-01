package oro_project.classes;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Customers")
public class Customer{
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	private String name;
	private String surname;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
	private Set<Order> orders = new HashSet<Order>();
	public Customer(){

	}
	public Customer(String name, String surname) {
		this.name = name;
		this.surname = surname;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	@Override
	public String toString() {
		return name + ", " + surname;
	}


}
