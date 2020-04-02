package com.balajinagar.admin.io.repositories;

import com.balajinagar.admin.io.entity.HouseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface HouseRepository extends PagingAndSortingRepository<HouseEntity, Long> {
    HouseEntity findHouseByHouseId(String houseId);
    HouseEntity findHouseByHouseNo(String houseNo);

    @Query(value = "select * from houses where house_id IN (select house_id from residents where first_name LIKE %:firstName% and family_head = 1)", nativeQuery = true)
    Page<HouseEntity> findHouseByFamilyHeadName(@Param("firstName") String firstName, Pageable pageable);

    @Query(value = "select * from houses order by house_no+0 ASC", nativeQuery = true)
    Page<HouseEntity> findAll(Pageable pageable);

    @Query(value = "select * from houses where house_id IN (select house_id from residents where first_name LIKE %:firstName)", nativeQuery = true)
    Page<HouseEntity> findAllByFirstName(@Param("firstName") String firstName, Pageable pageable);
}
