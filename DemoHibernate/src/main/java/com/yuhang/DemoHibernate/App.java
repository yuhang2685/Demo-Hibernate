package com.yuhang.DemoHibernate;

import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
//import org.hibernate.service.ServiceRegistryBuilder;


/**
 * 
 * @author yuhang
 *
 * In this demo, we explore how to setup and use the ORM tool Hibernate which implements JPA.
 * 
 * We need: 
 * dependencies Hibernate and MySQL;
 * the hibernate plugin (JBoss/hibernate) in Eclipse MarketPlace;
 * the configuration of MySQL in hibernate.cfg.xml.
 * 
 * Then we can use "Session" which is an interface between the application and Hibernate.
 * We explore how to fetch object from and save object into DB,
 * we also explore how to map object relations,
 * and we use Hibernate Query Language (HQL) for complicated ORM manipulations.
 * which different to SQL in that HOL manipulates objects but SQL manipulates DB.
 * 
 * Future work:
 * Practice how to improve the efficiency by Hibernate caching.
 */

public class App 
{
    public static void main( String[] args )
    {
    	exampleHQLSQL();
    	
    }
    
    /**
     * @author yuhang
     * 
     * In this example, we explore how to setup Hibernate Session, fetch and save objects in DB.
     * 
     */
     
    public static void exampleFetchSave() {    

    	// Dummy data
    	Alien alien = new Alien();
    	alien.setAid(104);
    	alien.setAname("SOPHIA");
    	alien.setPoints(65);
    	
    	// Create the configuration file and specify the class we use.
    	Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);

    	// Different to Telusko tutorial since what he used was deprecated.
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	// Session factory for creating Sessions
    	SessionFactory sf = con.buildSessionFactory(reg);    	
    	
    	// Create a Session
    	Session session = sf.openSession();
    			
    	// Achieves DB ACID properties using transaction, for example write into DB.
    	Transaction tx = session.beginTransaction();
    	
    	// Create table in DB if not exists when saving object into DB.
    	session.save(alien);
    	
    	tx.commit();
    	
    	// Fetch object from MySQL using Hibernate
    	alien = (Alien) session.get(Alien.class, 101);    	
    	System.out.println(alien);
    }
    
    /**
     * 
     * @author yuhang
     * 
     * In this example, we explore the one-one mapping relationship, e.g., one student has one laptop.
     * Class Student has a field Laptop, which is annotated by @OneToOne. 
     * It generates 2 tables, where table Student has table Laptop's primary key as foreign key.
     * 
     */
     
    public static void exampleOneToOne() {
    	/*
    	// Dummy data
    	Laptop l1 = new Laptop();
    	l1.setLid(1001);
    	l1.setLname("TOSHIBA");
    	
    	Student s = new Student();
    	s.setRollnum(101);
    	s.setName("YUHANG");
    	s.setMarks(75);
    	s.setLaptop(l1);
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(l1);
    	session.save(s);
    	
    	tx.commit();
      	*/
    }
    
    /**
     * 
     * @author yuhang
     * 
     * In this example, we explore the one-many mapping relationship, e.g., one student has many laptops.
     * Class Student has a field Laptops, which is annotated by @OneToMany. 
     * It generates 3 tables (one for one-many relation):
     * table Student has no laptop information;
     * table Laptop has no student information (due to no student field);  
     * table student_laptop preserves the student-laptop relationship.
     * 
     */

    public static void exampleOneToMany1() {
    	
    	// Dummy data
    	Laptop l1 = new Laptop();
    	l1.setLid(1001);
    	l1.setLname("TOSHIBA");
    	Laptop l2 = new Laptop();
    	l2.setLid(1002);
    	l2.setLname("DELL");
    	
    	Student s = new Student();
    	s.setRollnum(101);
    	s.setName("YUHANG");
    	s.setMarks(75);
    	s.getLaptops().add(l1);
    	s.getLaptops().add(l2);
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(l1);
    	session.save(l2);
    	session.save(s);
    	
    	tx.commit();
    
    	
    }

    /**
     * 
     * @author yuhang
     * 
     * In this example, we explore one-many mapping relationship again, e.g., one student has many laptops,
     * But class Student has a field Laptops, which is annotated by @OneToMany,
     * and class Laptop has a field Student, which is annotated by @ManyToOne,
     * It generates 3 tables (one for one-many relation):
     * table Student has no laptop information;
     * table Laptop has student information;
     * table student_laptop preserves the student-laptop relationship.
     * 
     */

    public static void exampleOneToMany2() {
    /*	
    	// Dummy data
    	Laptop l1 = new Laptop();
    	l1.setLid(1001);
    	l1.setLname("TOSHIBA");
    	Laptop l2 = new Laptop();
    	l2.setLid(1002);
    	l2.setLname("DELL");
    	
    	Student s = new Student();
    	s.setRollnum(101);
    	s.setName("YUHANG");
    	s.setMarks(75);
    	
    	l1.setStud(s);
    	l2.setStud(s);
    	
    	s.getLaptops().add(l1);
    	s.getLaptops().add(l2);
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(l1);
    	session.save(l2);
    	session.save(s);
    	
    	tx.commit();
    */
    	
    }

    /**
     * 
     * @author yuhang
     * 
     * In this example, we explore one-many mapping relationship again, e.g., one student has many laptops,
     * But class Student has a field Laptops, which is annotated by @OneToMany(mappedBy="stud"),
     * and class Laptop has a field Student, which is annotated by @ManyToOne,
     * It generates 2 tables:
     * table Student has no laptop information;
     * table Laptop has student information;
     * 
     */

    public static void exampleOneToMany3() {
    /*	
    	// Dummy data
    	Laptop l1 = new Laptop();
    	l1.setLid(1001);
    	l1.setLname("TOSHIBA");
    	Laptop l2 = new Laptop();
    	l2.setLid(1002);
    	l2.setLname("DELL");
    	
    	Student s = new Student();
    	s.setRollnum(101);
    	s.setName("YUHANG");
    	s.setMarks(75);
    	
    	l1.setStud(s);
    	l2.setStud(s);
    	
    	s.getLaptops().add(l1);
    	s.getLaptops().add(l2);
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(l1);
    	session.save(l2);
    	session.save(s);
    	
    	tx.commit();    
    */
    }
   
    /**
     * 
     * @author yuhang
     * 
     * In this example, we explore many-many mapping relationship again, 
     * e.g., one student uses many laptops, one laptop is used by many students.
     * But class Student has a field Laptops, which is annotated by @ManyToMany,
     * and class Laptop has a field Students, which is annotated by @ManyToMany,
     * It generates 4 tables:
     * table Student has no laptop information;
     * table Laptop has no student information;
     * table laptop_student preserves the relation between laptop and student;
     * table student_laptop preserves the relation between student and laptop.
     * 
     * We can use @ManyToMany(mappedBy="") to reduce one table preserving the relation.
     */

    public static void exampleManyToMany() {
    	
    	// Dummy data
    	Laptop l1 = new Laptop();
    	l1.setLid(1001);
    	l1.setLname("TOSHIBA");
    	Laptop l2 = new Laptop();
    	l2.setLid(1002);
    	l2.setLname("DELL");
    	
    	Student s1 = new Student();
    	s1.setRollnum(101);
    	s1.setName("YUHANG");
    	s1.setMarks(75);
    	Student s2 = new Student();
    	s2.setRollnum(102);
    	s2.setName("JINFANG");
    	s2.setMarks(85);
    	
    	l1.getStuds().add(s1);
    	l1.getStuds().add(s2);
    	l2.getStuds().add(s1);
    	
    	s1.getLaptops().add(l1);
    	s1.getLaptops().add(l2);
    	s2.getLaptops().add(l1);
    	
    	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Student.class).addAnnotatedClass(Laptop.class);
    	
    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    
    	
    	Session session = sf.openSession();
    	
    	Transaction tx = session.beginTransaction();
    	
    	session.save(l1);
    	session.save(l2);
    	session.save(s1);
    	session.save(s2);
    	
    	tx.commit();    
    
    }

    /**
     * @author yuhang
     * 
     * In this example, we explore how to use Hibernate Query Language (HQL) for complicated ORM manipulations.
     * 
     */
     
    public static void exampleHQL() {    
	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);

    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    	
    	
    	Session session = sf.openSession();
    			
    	Transaction tx = session.beginTransaction();
    	
    	/* Save dummy data:
    	Random rd = new Random();    	
    	for (int i = 1; i <= 50; i++) 
    	{
    		Alien a = new Alien();
    		a.setAid(i);
    		a.setAname("name" + i);
    		a.setPoints(rd.nextInt(100));
    		session.save(a);		
    	}
    	*/
    	
    	// HQL query, note capital A for class.
    	Query q1 = session.createQuery("from Alien where points > 50");
    	List<Alien> aliens = q1.list();		
    	for (Alien a: aliens) {System.out.println(a);}
    	
    	// HQL query, for single result.
    	Query q2 = session.createQuery("from Alien where aid = 7");
    	Alien a = (Alien) q2.uniqueResult();
    	System.out.println(a);
    	
    	// HQL query, fetching specific columns (as objects array) instead of the entire record.
    	Query q3 = session.createQuery("select aid, aname, points from Alien where aid = 7");
    	Object[] s = (Object[]) q3.uniqueResult();
    	for (Object o: s) {System.out.println(o);}
    	
    	// HQL query, fetching specific columns of all records.
    	Query q4 = session.createQuery("select aid, aname, points from Alien");
    	List<Object[]> g = (List<Object[]>) q4.list();
    	for (Object[] t : g) {System.out.println(t[0] + ":" + t[1] + ":" + t[2]);}
    	
    	// HQL query, fetching the sum of specific column of specific records using "align"
    	Query q5 = session.createQuery("select sum(points) from Alien a where a.points>50");
    	Long y = (Long) q5.uniqueResult();
    	System.out.println(y);
    	
    	// HQL query, using variable.
    	int p = 60;
    	Query q6 = session.createQuery("select sum(points) from Alien a where a.points> :p");
    	q6.setParameter("p", p);
    	Long z = (Long) q6.uniqueResult();
    	System.out.println(z);
    	
    	tx.commit(); 	
    }
   
    /**
     * @author yuhang
     * 
     * In this example, we explore how to embed SQL into Hibernate Query Language (HQL).
     * 
     */
     
    public static void exampleHQLSQL() {    
	
    	Configuration con = new Configuration().configure().addAnnotatedClass(Alien.class);

    	ServiceRegistry reg = new StandardServiceRegistryBuilder().applySettings(con.getProperties()).build();
    	
    	SessionFactory sf = con.buildSessionFactory(reg);    	
    	
    	Session session = sf.openSession();
    			
    	Transaction tx = session.beginTransaction();

    	// Also called "Native Queries..."
    	SQLQuery q1 = session.createSQLQuery("select * from alien where points > 60");
    	q1.addEntity(Alien.class);
    	List<Alien> aliens = q1.list();    	
    	for (Alien a : aliens) {System.out.println(a);}
    	
    	SQLQuery q2 = session.createSQLQuery("select aname,points from alien where points > 60");
    	// Convert output into map format
    	q2.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
    	List s = q2.list();    	
    	for (Object o : s) 
    	{
    		Map m = (Map)o;
    		System.out.println(m.get("aname") + ":" + m.get("points"));
    	}
    	
    	tx.commit(); 	
    }
   

}
