package com.balajinagar.admin.io.repositories;

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
    ResidentEntity findByResidentId(String residentId);
    List<ResidentEntity> findByFirstName(String name);
    Page<ResidentEntity> findAllByOrderByFirstName(Pageable pageable);
    Page<ResidentEntity> findByFirstNameOrderByFirstName(String firstName, Pageable pageable);
    Page<ResidentEntity> findAllBySex(String sex, Pageable pageable);
    List<ResidentEntity> findAllByHouseId(String houseId);

    @Transactional
    @Modifying
    @Query(value = "delete from residents where house_id = :houseId",nativeQuery = true)
    void deleteAllByHouseId(@Param("houseId") String houseId);

    @Query(value = "select * from residents where house_id = (select house_id from houses where house_no = :houseNo)", nativeQuery = true)
    List<ResidentEntity> getResidentsByHouseNo(@Param("houseNo") String houseNo);

    @Transactional
    @Query(value = "select count(*) from residents",nativeQuery = true)
    int getNumberOfResidents();

    @Query(value = "select * from residents where first_name = :firstName and family_head = 0x01 order by first_name", nativeQuery = true)
    Page<ResidentEntity> findResidentsByFamilyHeadName(@Param("firstName") String firstName, Pageable pageable);
}
