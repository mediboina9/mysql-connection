package com.example.mysqlconnection;

import com.example.mysqlconnection.model.EMP;
import com.example.mysqlconnection.repo.EmpCrudRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTRst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.FileReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class MysqlConnectionApplication implements CommandLineRunner {
@Autowired
EmpCrudRepo empCrudRepo;
	public static void main(String[] args) {
		SpringApplication.run(MysqlConnectionApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		EMP tarun=new EMP(1,"Tarun");
		EMP satya=new EMP(2,"satya");
		EMP Bhavya =new EMP(3,"Bhavya");
		EMP Narashimha=new EMP(4,"Narashimha");
		EMP siva=new EMP(5,"satya");



		empCrudRepo.save(tarun);
		//empCrudRepo.save(obj1);
		String nullName = null;
		String name = Optional.ofNullable(nullName).orElse("john");
		System.out.println("..........................."+name+"..........................");
		int name1 = getRandomName();
		System.out.println(name1);

		JSONParser parser = new JSONParser();
		//Use JSONObject for simple JSON and JSONArray for array of JSON.
		JSONObject data = (JSONObject) parser.parse(
				new FileReader("src/main/resources/empJsonData.json"));//path to the JSON file.

		String json = data.toJSONString();
		System.out.println(json);
		ContextLoader ctx=null;

		ObjectMapper objectMapper=new ObjectMapper();
		EMP emp=objectMapper.readValue(json,EMP.class);
		System.out.println(emp);
		System.out.println('j'+'a'+'v'+'a');//asciii value of 97
		System.out.println('j');
		System.out.println("First");

		ArrayList<EMP> arrayList=new ArrayList<>();
		arrayList.add(tarun);
		arrayList.add(satya);
		arrayList.add(Bhavya);
		arrayList.add(Narashimha);
		arrayList.add(siva);
		arrayList.stream().filter(i->i.getId()>1).map(i->i.getName()).forEach(System.out::println);
		Stream<EMP> streamEmp=arrayList.stream();
		streamEmp.map(i->i.getId()).forEach(System.out::println);
		//ArrayList<EMP> empSortedArrayList=ArrayList(streamEmp.sorted(Comparator.comparing(EMP::getName)).collect(Collectors.toList()));
		//empSortedArrayList.forEach(System.out::println);
		System.out.println("for loop emp list");
		for(EMP e:arrayList){
			System.out.println(e);
		}

		String message="sorted order";
		System.out.println(message);
		arrayList.stream().sorted(Comparator.comparing(EMP::getName).reversed().thenComparingInt(EMP::getId)).forEach(System.out::println);
		System.out.println("Seperate sorted methods:");
		arrayList.stream().sorted(Comparator.comparing(EMP::getId)).sorted(Comparator.comparing(EMP::getName).reversed()).forEach(System.out::println);


		//webfluxData(obj);





	}
	public void webfluxData(EMP emp){
		WebClient builder =WebClient.create("http://localhost:8080");
		WebClient.RequestHeadersSpec<?> errorResponse=builder.post()
				.uri("/")
				.body(Mono.just(emp),EMP.class);

		System.out.println(12);
	}



	private CTRst st;

	public int getRandomName() {
		System.out.println("getRandomName() method - start");

		Random random = new Random();
		int index = random.nextInt(100);

		System.out.println("getRandomName() method - end"+index);
		return index;
	}





}
