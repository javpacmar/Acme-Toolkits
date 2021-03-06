
package acme.testing.any.item.list;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import acme.testing.TestHarness;

public class AnyItemsListTest extends TestHarness {

	@ParameterizedTest
	@CsvFileSource(resources = "/any/item/itemComponent.csv", encoding = "utf-8", numLinesToSkip = 1)
	@Order(10)
	public void positiveTestComponents(final int recordIndex, final String name, final String code, final String technology, final String description, final String price, final String link, final String inventor) {

		super.clickOnMenu("Anonymous", "List Component");
		super.checkListingExists();
		super.sortListing(1, "asc");

		
		super.checkColumnHasValue(recordIndex, 0, name);
		super.checkColumnHasValue(recordIndex, 1, code);
		super.checkColumnHasValue(recordIndex, 2, technology);
		super.checkColumnHasValue(recordIndex, 3, price);

		super.clickOnListingRecord(recordIndex);
		super.checkFormExists();
		super.checkInputBoxHasValue("name", name);
		super.checkInputBoxHasValue("code", code);
		super.checkInputBoxHasValue("technology", technology);
		super.checkInputBoxHasValue("description", description);
		super.checkInputBoxHasValue("price", price);
		super.checkInputBoxHasValue("link", link);

	}
		@ParameterizedTest
		@CsvFileSource(resources = "/any/item/itemTools.csv", encoding = "utf-8", numLinesToSkip = 1)
		@Order(10)
		public void positiveTestTools(final int recordIndex, final String name,
			final String code, final String technology, final String description, final String price, final String link, final String inventor) {
	
			super.clickOnMenu("Anonymous", "List Tools");
			super.checkListingExists();
			super.sortListing(1, "asc");
	
			super.checkColumnHasValue(recordIndex, 0, name);
			super.checkColumnHasValue(recordIndex, 1, code);
			super.checkColumnHasValue(recordIndex, 2, technology);
			super.checkColumnHasValue(recordIndex, 3, price);
	
			super.clickOnListingRecord(recordIndex);
			super.checkFormExists();
			super.checkInputBoxHasValue("name", name);
			super.checkInputBoxHasValue("code", code);
			super.checkInputBoxHasValue("technology", technology);
			super.checkInputBoxHasValue("description", description);
			super.checkInputBoxHasValue("price", price);
			super.checkInputBoxHasValue("link", link);
	
		}
}
