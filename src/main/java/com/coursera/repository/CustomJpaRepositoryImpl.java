package com.coursera.repository;

import com.coursera.vo.StudentEnrollments;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CustomJpaRepositoryImpl implements CustomJpaRepository {

    private final EntityManager entityManager;

    public CustomJpaRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<StudentEnrollments> findAllEnrollments(String createdBy) {
        StringBuilder query = new StringBuilder("SELECT u.userName as userName,count(*) as enrollmentCount FROM User u JOIN UserCourseDtl ucd on u.id = ucd.userId ");
        query.append(" where u.role='STUDENT'");
        if (StringUtils.isNotEmpty(createdBy)) {
            query.append(" and 1 = (select 1 from Course c where c.id = ucd.courseId and c.createdBy = '").append(createdBy).append("' )");
        }
        query.append(" group by userName");
        List<Object[]> resultList
                = (List<Object[]>) entityManager.createQuery(query.toString()).getResultList();
        return resultList.stream().map(data -> new StudentEnrollments(data[0].toString(), (Long) data[1])).collect(Collectors.toList());
    }
}
