package com.Ged.demoSpringFramework.repository;

import com.Ged.demoSpringFramework.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DocRepository extends JpaRepository<Document,Integer> {
    List<Document> findAllByOrderByDocNameAsc();
    @Query(value = " select * from ged.document where ged.document.doc_name like %:keyword% ", nativeQuery = true)
    List<Document> findByKeyWord(@Param("keyword") String keyword);
}
