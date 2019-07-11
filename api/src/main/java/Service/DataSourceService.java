package Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import Dao.HealthAverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("Service.DataSourceService")
public class DataSourceService {

    Logger logger = LoggerFactory.getLogger(DataSourceService.class);

    @Autowired
    public DataSource dataSource;

    public HealthAverage defaultValue() {
        return new HealthAverage();
    }

    @Value("${querylimit}")
    private String querylimit;

    public List<HealthAverage> search() {

        HealthAverage d1 = new HealthAverage();
        d1.setYear("2019");
        d1.setQuestion("what");
        d1.setAverage("35.5");
        List<HealthAverage> list = new ArrayList<HealthAverage>();
        list.add(d1);

        return list;
    }
}
