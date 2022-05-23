package acme.testing.inventor.toolkits;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class InventorToolkitPublishedTest extends TestHarness {
	

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/toolkit/publishedToolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveToolkitTest(final int recordIndex, final String code, final String title, 
		final String description, final String notes, final String link, final String published) {

		super.signIn("inventor01", "inventor01");
		super.clickOnMenu("Inventor", "Toolkit list");

		super.checkListingExists();
		super.checkNotListingEmpty();

		super.sortListing(3, "asc");

		super.checkColumnHasValue(recordIndex, 3, published);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();

		super.clickOnSubmit("Publish");

		super.checkNotErrorsExist();

		super.signOut();

	}

	@ParameterizedTest
	@CsvFileSource(resources = "/inventor/toolkit/negativePublishedToolkit.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void negativeToolkitTest(final int recordIndex, final String code, final String title, 
		final String description, final String notes, final String link, final String published) {

		super.signIn("inventor01", "inventor01");
		super.clickOnMenu("Inventor", "Toolkit list");

		super.checkListingExists();
		super.checkNotListingEmpty();

		super.sortListing(3, "asc");

		super.checkColumnHasValue(recordIndex, 3, published);
		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkNotButtonExists("Publish");

		super.signOut();

	}

}
