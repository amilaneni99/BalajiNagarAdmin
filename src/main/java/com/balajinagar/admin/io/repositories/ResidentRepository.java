package com.balajinagar.admin.io.repositories;

import com.balajinagar.admin.io.entity.HouseEntity;
import com.balajinagar.admin.io.entity.ResidentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ResidentRepository extends PagingAndSortingRepository<ResidentEntity, Long> {
    List<ResidentEntity> findAllByHouseDetails(HouseEntity houseEntity);
    ResidentEntity findByResidentId(String residentId);
    List<ResidentEntity> findByFirstName(String name);
    Page<ResidentEntity> findAllByOrderByFirstName(Pageable pageable);
    Page<ResidentEntity> findAllBySex(String sex, Pageable pageable);

    @Transactional
    @Modifying
    @Query(value = "delete from residents where house_id = :houseId",nativeQuery = true)
    void deleteAllByHouseId(@Param("houseId") String houseId);
}
