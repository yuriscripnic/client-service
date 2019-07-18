package yss.clientservice.repository;

import yss.clientservice.entity.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoDataRepository extends JpaRepository<GeoData, Long> {
}
