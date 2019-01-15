package io.pivotal.support;

import java.util.Optional;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.config.annotation.EnablePdx;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

// @ClientCacheApplication(locators =
//		{@ClientCacheApplication.Locator(host = "10.232.232.123", port = 55221)})
@EnableGemfireRepositories
@EnableEntityDefinedRegions
@EnablePdx
@SpringBootApplication
public class ClientApp {

	public static void main(String args[]) {

		new SpringApplicationBuilder(ClientApp.class)
				.web(WebApplicationType.NONE)
				.build()
				.run(args);
	}

	/**
	 * Create a region using GFSH command
	 * create region --name=/Customer --type=REPLICATE --entry-idle-time-expiration=60 --entry-idle-time-expiration-action=DESTROY --enable-statistics
	 *
	 *
	 * 1. Loads 10 Customers into Customer region which has TTI set to 60 sec
	 * 2. Waits for 30 secs and does a get on Customer5
	 * 3. Waits for 50 secs and does a get on Customer5 again. Succesfully finds it.
	 * 4. Then does a get on Customer8 which will not be found as expected.
	 * @param customerRepository
	 * @return
	 */
	@Bean ApplicationRunner runner(CustomerRepository customerRepository) {
		return args -> {

			for (int i = 0; i < 10; i++) {
				customerRepository.save(Customer.of(i,"Name"+i));
			}

			Thread.sleep(30*1000);
			Optional<Customer> firstAttemptCustomer = customerRepository.findById(5);
			System.out.println("$$$ FirstAttempt "+firstAttemptCustomer.toString());
			if (!firstAttemptCustomer.isPresent()){
				System.err.println("$$$ ERROR firstAttemptCustomer NOT FOUND");
			}else {
				System.out.println("$$$ firstAttemptCustomer found: " + firstAttemptCustomer);
			}

			//TTI expiration set to 60 sec, So after this sleep, if customer
//			object is found then the lastaccesstime is updated correctly.
			Thread.sleep(50 * 1000);

			Optional<Customer> secondAttemptCustomer = customerRepository.findById(5);
			if (!secondAttemptCustomer.isPresent()){
				System.err.println("$$$ ERROR secondAttemptCustomer NOT FOUND");
			}else {
				System.out.println("$$$ secondAttemptCustomer found: " + secondAttemptCustomer);
			}

			// DLW: let's make sure it does actually delete if we wait longer than 60 seconds
//			object is found then the lastaccesstime is updated correctly.
			Thread.sleep(40 * 1000);

			Optional<Customer> thirdAttemptCustomer = customerRepository.findById(5);
			System.out.println("$$$ ThirdAttempt "+thirdAttemptCustomer.toString());
			if (thirdAttemptCustomer.isPresent()){
				System.err.println("$$$ ERROR thirdAttemptCustomer FOUND:" + thirdAttemptCustomer);
			}else {
				System.out.println("$$$ thirdAttemptCustomer not found");
			}


			//customer8 should not be found, as we did not a get within 60 sec of put
			Optional<Customer> shouldNotBeFoundCustomer = customerRepository.findById(8);
			if (shouldNotBeFoundCustomer.isPresent()){
				System.err.println("$$$ ERROR FOUND EXPIRED ENTRY "+shouldNotBeFoundCustomer);
			}else {
				System.out.println("$$$ Expired customer not found");
			}
		};
	}
}
