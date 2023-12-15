package com.example.paoim4.back;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaDelete;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class DatabaseManager {
    private static DatabaseManager INSTANCE;
    private SessionFactory sessionFactory;

    private DatabaseManager(){
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        try {
            sessionFactory = new MetadataSources(registry)
                    .addAnnotatedClass(Teacher.class)
                    .addAnnotatedClass(TeacherGroup.class)
                    .addAnnotatedClass(Rate.class)
                    .buildMetadata().buildSessionFactory();
        }catch (Exception e){
            System.out.println(e.getMessage());
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    public static DatabaseManager getInstance(){
        if(INSTANCE == null)
            INSTANCE = new DatabaseManager();

        return INSTANCE;
    }

    public void saveGroup(TeacherGroup teacherGroup){
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(teacherGroup);
            session.getTransaction().commit();
        }
    }

    public void deleteGroup(TeacherGroup TeacherGroup) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();

            CriteriaDelete<Teacher> criteriaDeleteTeacher = builder.createCriteriaDelete(Teacher.class);
            Root<Teacher> rootTeacher = criteriaDeleteTeacher.from(Teacher.class);
            criteriaDeleteTeacher.where(builder.equal(rootTeacher.get("teacherGroup"), TeacherGroup));

            session.createQuery(criteriaDeleteTeacher).executeUpdate();

            CriteriaDelete<Rate> criteriaDeleteRate = builder.createCriteriaDelete(Rate.class);
            Root<Rate> rootRate = criteriaDeleteRate.from(Rate.class);
            criteriaDeleteRate.where(builder.equal(rootRate.get("teacherGroup"), TeacherGroup));

            session.createQuery(criteriaDeleteRate).executeUpdate();

            CriteriaDelete<TeacherGroup> criteriaDeleteTeacherGroup = builder.createCriteriaDelete(TeacherGroup.class);
            Root<TeacherGroup> rootTeacherGroup = criteriaDeleteTeacherGroup.from(TeacherGroup.class);
            criteriaDeleteTeacherGroup.where(builder.equal(rootTeacherGroup, TeacherGroup));

            session.createQuery(criteriaDeleteTeacherGroup).executeUpdate();

            session.getTransaction().commit();
        }
    }

    public List<TeacherGroup> getAllGroups() {
        List<TeacherGroup> TeacherGroups;
        try(Session session = sessionFactory.openSession()){
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TeacherGroup> criteriaQuery = cb.createQuery(TeacherGroup.class);
            Root<TeacherGroup> root = criteriaQuery.from(TeacherGroup.class);
            criteriaQuery.select(root);
            Query<TeacherGroup> query = session.createQuery(criteriaQuery);
            TeacherGroups = query.getResultList();
        }
        return TeacherGroups;
    }

    public void saveTeacher(Teacher newTeacher) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(newTeacher);
            session.getTransaction().commit();
        }
    }

    public void deleteTeacher(Teacher Teacher) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.remove(Teacher);
            session.getTransaction().commit();
        }
    }

    public void modifyTeacher(Teacher Teacher) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(Teacher);
            session.getTransaction().commit();
        }
    }

    public List<Teacher> getTeacherListByGroup(TeacherGroup TeacherGroup) {
        List<Teacher> TeacherList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Teacher> query = builder.createQuery(Teacher.class);
            Root<Teacher> root = query.from(Teacher.class);

            query.select(root).where(builder.equal(root.get("teacherGroup"), TeacherGroup));

            TeacherList = session.createQuery(query).getResultList();
        }
        return TeacherList;
    }

    public List<Rate> getRateListByGroup(TeacherGroup TeacherGroup) {
        List<Rate> rateList;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Rate> query = builder.createQuery(Rate.class);
            Root<Rate> root = query.from(Rate.class);

            query.select(root).where(builder.equal(root.get("teacherGroup"), TeacherGroup));

            rateList = session.createQuery(query).getResultList();
        }
        return rateList;
    }

    public void saveRate(Rate rate) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.persist(rate);
            session.getTransaction().commit();
        }
    }

    public List<Teacher> getAllTeachers() {
        List<Teacher> Teachers;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Teacher> criteriaQuery = cb.createQuery(Teacher.class);
            Root<Teacher> root = criteriaQuery.from(Teacher.class);
            criteriaQuery.select(root);
            Query<Teacher> query = session.createQuery(criteriaQuery);
            Teachers = query.getResultList();
        }
        return Teachers;
    }

    public List<Rate> getAllRates() {
        List<Rate> rates;
        try (Session session = sessionFactory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Rate> criteriaQuery = cb.createQuery(Rate.class);
            Root<Rate> root = criteriaQuery.from(Rate.class);
            criteriaQuery.select(root);
            Query<Rate> query = session.createQuery(criteriaQuery);
            rates = query.getResultList();
        }
        return rates;
    }

    public void updateTeacher(Teacher teacher) {
        try(Session session = sessionFactory.openSession()){
            session.beginTransaction();
            session.merge(teacher);
            session.getTransaction().commit();
        }
    }

    public void exportToCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/TeacherGroup.csv"))) {
            StatefulBeanToCsv<TeacherGroup> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<TeacherGroup>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsvEmployeeGroup.write(DatabaseManager.getInstance().getAllGroups());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Teacher.csv"))) {
            StatefulBeanToCsv<Teacher> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<Teacher>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();
            beanToCsvEmployeeGroup.write(DatabaseManager.getInstance().getAllTeachers());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/resources/Rate.csv"))) {
            StatefulBeanToCsv<Rate> beanToCsvEmployeeGroup = new StatefulBeanToCsvBuilder<Rate>(writer)
                    .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsvEmployeeGroup.write(DatabaseManager.getInstance().getAllRates());

            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
