package oro_project;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import oro_project.classes.*;


public class MainClass{
	
	public static Session session;
	private static int x=1;

	public static void connect_with_database(){
		SessionFactory factory = HibernateUtil.buildSessionFactory();
        session = factory.openSession();
	}
	public static void first_use(){
		Transaction tx = session.beginTransaction();
		Product p1 = new Product("Pepsi", 30, 3.99);
		Product p2 = new Product("Fanta", 20, 1.99);
		Product p3 = new Product("Cola", 30, 2.49);
		Product p4 = new Product("Sprite", 15, 4.99);
		Product p5 = new Product("Mirinda", 10, 2.99);
		List<Product> lp1 = new ArrayList<Product>();
		lp1.add(p1);
		lp1.add(p2);
		lp1.add(p3);
		List<Product> lp2 = new ArrayList<Product>();
		lp1.add(p2);
		lp1.add(p4);
		Address a1 = new Address("14","Aleja Politechniki","Lodz");
		Address a2 = new Address("12","Pilsudskiego","Krakow");
		Address a3 = new Address("15","Wielkopolska","Gdansk");
		Address a4 = new Address("19","Pilsudskiego","Warszawa");
		Address a5 = new Address("22","Obozowa","Poznan");
		Customer c1 = new Customer("Anna","Nowak");
		Customer c2 = new Customer("Tomasz","Goralski");
		Customer c3 = new Customer("Wojciech","Buczek");
		Customer c4 = new Customer("Katarzyna","Zielinska");
		Customer c5 = new Customer("Krzysztof","Jamroz");
		Order o1 = new Order();
		Order o2 = new Order();
		Order o3 = new Order();
		Order o4 = new Order();
		Order o5 = new Order();
		
		a1.setCustomer(c1);
		a2.setCustomer(c2);
		a3.setCustomer(c3);
		a4.setCustomer(c4);
		a5.setCustomer(c5);
		
		o1.setProducts(lp1);
		o2.setProducts(lp2);
		o3.setProducts(lp1);
		o4.setProducts(lp2);
		o5.setProducts(lp1);
		
		o1.setCustomer(c1);
		o2.setCustomer(c2);
		o3.setCustomer(c3);
		o4.setCustomer(c4);
		o5.setCustomer(c5);
			
		session.save(a1);	session.save(a2);	session.save(a3);
		session.save(a4);	session.save(a5);	session.save(p1);
		session.save(p2);	session.save(p3);	session.save(p4);
		session.save(p5);	session.save(c1);	session.save(c2);
		session.save(c3);	session.save(c4);	session.save(c5);
		session.save(o1);	session.save(o2);	session.save(o3);
		session.save(o4);	session.save(o5);
		tx.commit();
	}
	
	public static void main(String[] args) {
		connect_with_database();
		first_use();
		System.out.println("Witaj w naszej bazie danych! ");
		while(x != 0) {
			System.out.println("Wybierz opcje z menu. \n 1.Pokaz produkty \n 2.Pokaz klientki sklepu \n 3.Pokaz klientow i ich adresy \n 4.Dodaj produkt \n 5.Usun produkt \n 0.Wyjdz");
			Scanner scanner = new Scanner(System.in);
			x = scanner.nextInt();
			switch (x) {
		    case 1:
		      Query1();
		      break;
		    case 2:
			  Query2();
			  break;
		    case 3:
			  Query3();
			  break;
		    case 4:
		      System.out.println("Podaj nazwe produktu: ");
		      Scanner sc = new Scanner(System.in);
		      String produkt = sc.nextLine();
		      System.out.println("Podaj ilosc na magazynie: ");
		      Scanner sc2 = new Scanner(System.in);
		      Integer ilosc = sc2.nextInt();
		      System.out.println("Podaj cene za sztuke: ");
		      Scanner sc3 = new Scanner(System.in).useLocale(Locale.US);
		      Double cena = sc3.nextDouble();
		      Query4(produkt,ilosc,cena);
		      break;
		    case 5:
			  System.out.println("Podaj id produktu do usuniecia: ");
		      Scanner sc4 = new Scanner(System.in);
		      int id = sc4.nextInt();
			  Query5(id);
			  break;
		    case 0:
			  System.out.println("Do widzenia.");
			  break;
		    default:
		      System.out.println("Nieprawidlowy numer.");
		  }
		}
		
	}
	
	private static void Query1() {
	String hql = "select x.id, x.name, x.amount, x.priceEach from Product x";
	Query query = session.createQuery(hql);
    @SuppressWarnings("unchecked")
	List results = query.list();    
	System.out.println(Arrays.deepToString(results.toArray()));	
	} 
	
	private static void Query2() {
	//String hql = "select x.name, x.priceEach from Product x WHERE x.priceEach>3.0";
	String hql = "select x.name, x.surname from Customer x WHERE x.name like '%a'";
	Query query = session.createQuery(hql);
    @SuppressWarnings("unchecked")
	List results = query.list();    
	System.out.println(Arrays.deepToString(results.toArray()));	
	} 
	
	private static void Query3() {
	String hql = "select x.customer, x.numberOfBuilding, x.street, x.city from Address x";
	Query query = session.createQuery(hql);
    @SuppressWarnings("unchecked")
	List results = query.list();    
	System.out.println(Arrays.deepToString(results.toArray()));	
	} 

	private static void Query4(String name, Integer amount, Double priceEach) {
	Query query = session.createSQLQuery("INSERT INTO products (name,amount,priceEach) VALUES (:name,:amount,:priceEach)");
	query.setParameter("name", name);
	query.setParameter("amount", amount);
	query.setParameter("priceEach", priceEach);
	query.executeUpdate();
	}
	
	private static void Query5(int product_id) {
	String hql = "DELETE FROM Product "  + 
	             "WHERE id = :product_id";
	Query query = session.createQuery(hql);
	query.setParameter("product_id", product_id);
	int result = query.executeUpdate();
	System.out.println("Rows affected: " + result);   
	}

}
