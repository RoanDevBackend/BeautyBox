package org.beautybox.repository;

import org.beautybox.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, String> {
    @Query("SELECT im.url " +
            "FROM Image im " +
            "where im.product.id = :productId  ")
    List<String> findByProductId(String productId);
}
