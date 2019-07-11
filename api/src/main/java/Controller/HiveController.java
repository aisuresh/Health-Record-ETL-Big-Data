package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import Dao.HealthAverage;
import Service.DataSourceService;

@RestController
@RequestMapping("/hive")
public class HiveController {

	@Autowired
	private DataSourceService dataSourceService;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@RequestMapping(value = "/database", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> showDatabaeses() {
		List<Map<String, Object>> rows = null;
		rows = jdbcTemplate.queryForList("show databases");
		return new ResponseEntity<List<Map<String, Object>>>(rows, HttpStatus.OK);
	}

	@RequestMapping(value = "/table", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> showTables() {
		List<Map<String, Object>> rows = null;
		rows = jdbcTemplate.queryForList("show tables");
		return new ResponseEntity<List<Map<String, Object>>>(rows, HttpStatus.OK);
	}

	@RequestMapping(value = "/femaleAverage", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> femaleAverage() {
		List<Map<String, Object>> rows = null;
		jdbcTemplate.execute("use result");
		rows = jdbcTemplate.queryForList("select * from female_average");

		return new ResponseEntity<List<Map<String, Object>>>(rows, HttpStatus.OK);
	}

	@RequestMapping(value = "/average", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Map<String, Object>>> average() {
		List<Map<String, Object>> rows = null;
		jdbcTemplate.execute("use result");
		rows = jdbcTemplate.queryForList("select * from age_group_average");

		return new ResponseEntity<List<Map<String, Object>>>(rows, HttpStatus.OK);
	}


}
