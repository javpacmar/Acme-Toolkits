package acme.testing.any.chirps;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyCreateChirpsTest extends TestHarness {
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/chirpCreate.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveChirpTest(final int recordIndex, final String title, final String author, final String body, final String email) {

		super.clickOnMenu("Anonymous", "List Chirps");

		super.checkListingExists();

		super.clickOnButton("Create chirp");

		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("confirmation", "true");

		super.clickOnSubmit("Create");

		super.clickOnMenu("Anonymous", "List Chirps");

		super.checkListingExists();
		super.checkNotListingEmpty();

		//super.sortListing(5, "desc");
		super.sortListing(0, "asc");
		
		super.checkColumnHasValue(recordIndex, 0, title);
		super.checkColumnHasValue(recordIndex, 1, author);
		super.checkColumnHasValue(recordIndex, 2, body);
		super.checkColumnHasValue(recordIndex, 3, email);

	}
	
	
	@ParameterizedTest
	@CsvFileSource(resources = "/any/chirp/chirpNoCreate.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(20)
	public void negativeTest(final int recordIndex, final String title, final String author,
			final String body, final String email, final String confirmation) {
		
		super.clickOnMenu("Anonymous", "List Chirps");

		super.checkListingExists();

		super.clickOnButton("Create chirp");
		
		super.fillInputBoxIn("title", title);
		super.fillInputBoxIn("author", author);
		super.fillInputBoxIn("body", body);
		super.fillInputBoxIn("email", email);
		super.fillInputBoxIn("confirmation", confirmation);
		
		super.clickOnSubmit("Create");
		
		super.checkErrorsExist();
	}


}
