/**
 * Mapper for ClientMerchant objects.
 *
 * @author alibadisos
 * @since 1.0.0
 */
package com.techner.tau.services.mapper;

/**
 * ClientMerchantMapper
 */
public interface ClientMerchantMapper {
	// /**
	// * Saves a client merchant.
	// *
	// * @param merchant client merchant to save
	// * @return merchant ID of the newly added merchant
	// */
	// Integer insertClientMerchant(ClientMerchant merchant);
	//
	// /**
	// * Update a client merchant.
	// *
	// * @param merchant client merchant to update
	// */
	// void updateClientMerchant(ClientMerchant merchant);
	//
	// /**
	// * Disable the given merchant.
	// * @param merchantId ID of the merchant to disable
	// */
	// void disableClientMerchant(Integer merchantId);
	//
	// /**
	// * Find a client merchant ID.
	// *
	// * @param merchant merchant to find
	// * @return merchant ID
	// */
	// Integer findClientMerchantId(ClientMerchant merchant);
	//
	// /**
	// * Find a client merchant
	// * @param merchantId merchant ID of the client merchant
	// * @return client merchant
	// */
	// ClientMerchant findClientMerchant(Integer merchantId);
	//
	// /**
	// * Count the merchants for the given search parameters.
	// * @param clientId legacy client ID
	// * @param organizationId organization ID
	// * @param projectId project ID
	// * @param startDate start of range to search by created at date
	// * @param endDate end of range to search by created at date
	// * @param fileAction file action
	// * @param ids merchant IDs to search by
	// * @param keywords merchant ID1, merchant ID2, merchant name or MCC for a
	// partial search
	// * @param isActive include active or inactive merchants
	// * @return number of merchants
	// */
	// Integer countMerchants(
	// @Param("organizationId") Integer organizationId,
	// @Param("startDate") String startDate,
	// @Param("endDate") String endDate,
	// @Param("fileAction") String fileAction,
	// @Param("fileSource") String fileSource,
	// @Param("ids") List<Integer> ids,
	// @Param("keywords") String keywords,
	// @Param("urlId") Integer urlId,
	// @Param("isActive") Boolean isActive);
	//
	// /**
	// * Find the merchants and paginate them for the given search parameters.
	// * @param sortBy field to sort by
	// * @param sortDir direction to sort by
	// * @param clientId legacy client ID
	// * @param organizationId organization ID
	// * @param projectId project ID
	// * @param startDate start of range to search by created at date
	// * @param endDate end of range to search by created at date
	// * @param fileAction file action
	// * @param ids merchant IDs to search by
	// * @param keywords merchant ID1, merchant ID2, merchant name or MCC for a
	// partial search
	// * @param isActive boolean value to get active or inactive merchants
	// * @param offset offset for pagination
	// * @param page size for pagination
	// * @return list of merchants
	// */
	// List<ClientMerchant> findMerchants(
	// @Param("sortBy") String sortBy,
	// @Param("sortDir") String sortDir,
	// @Param("organizationId") Integer organizationId,
	// @Param("startDate") String startDate,
	// @Param("endDate") String endDate,
	// @Param("fileAction") String fileAction,
	// @Param("fileSource") String fileSource,
	// @Param("ids") List<Integer> ids,
	// @Param("keywords") String keywords,
	// @Param("urlId") Integer urlId,
	// @Param("isActive") Boolean isActive,
	// @Param("offset") int offset,
	// @Param("count") int count);
	//
	// /**
	// * Find the merchants for the given search parameters.
	// * @param sortBy field to sort by
	// * @param sortDir direction to sort by
	// * @param clientId legacy client ID
	// * @param organizationId organization ID
	// * @param projectId project ID
	// * @param startDate start of range to search by created at date
	// * @param endDate end of range to search by created at date
	// * @param fileAction file action
	// * @param ids merchant IDs to search by
	// * @param keywords merchant ID1, merchant ID2, merchant name or MCC for a
	// partial search
	// * @param isActive boolean value to get active or inactive merchants
	// * @return list of merchants
	// */
	// List<ClientMerchant> findMerchantsUrls(@Param("sortBy") String sortBy,
	// @Param("sortDir") String sortDir,
	// @Param("clientId") Integer clientId, @Param("organizationId") Integer
	// organizationId,
	// @Param("projectId") Integer projectId, @Param("startDate") String
	// startDate,
	// @Param("endDate") String endDate, @Param("fileAction") String fileAction,
	// @Param("fileSource") String fileSource,
	// @Param("ids") List<Integer> ids,
	// @Param("keywords") String keywords,
	// @Param("urlId") Integer urlId,
	// @Param("isActive") Boolean isActive);
	//
	// /**
	// * Find the URL ID for the given SHA1 hash
	// * @param sha1 SHA1 hash of a URL
	// * @return URL ID
	// */
	// Integer findUrlId(String sha1);
	//
	// /**
	// * Create a new URL for the given URL object
	// * @param url URL to be added
	// */
	// Integer insertUrl(Url url);
	//
	// /**
	// * Find the ID for the given client merchant URL association
	// * @param url client merchant URL to find the ID for
	// * @return ID of the client merchant URL, null if none exists
	// */
	// Integer findClientMerchantUrlId(ClientMerchantUrl url);
	//
	// /**
	// * Create a new client merchant URL entry
	// * @param url merchant to URL relationship
	// */
	// void insertClientMerchantUrl(ClientMerchantUrl url);
	//
	// /**
	// * Find the URLs for the given merchant
	// * @param merchantId ID of the merchant to find the URLs for
	// * @return list of merchant URLs
	// */
	// List<ClientMerchantUrl> findClientMerchantUrls(Integer merchantId);
	//
	// List<String> findUrls(
	// @Param("organizationId") Integer organizationId,
	// @Param("keywords") String keywords,
	// @Param("isActive") Boolean isActive
	// );
	//
	// List<Url> searchUrls(
	// @Param("keywords") String keywords,
	// @Param("projectId") Integer projectId,
	// @Param("organizationId") Integer organizationId,
	// @Param("offset") Integer offset,
	// @Param("limit") Integer limit);
	//
	// Integer countUrls(
	// @Param("keywords") String keywords,
	// @Param("projectId") Integer projectId,
	// @Param("organizationId") Integer organizationId);
	//
	// /**
	// * Create a new project URL entry
	// * @param url project to URL relationship
	// */
	// void insertProjectUrl(ProjectUrl url);
	//
	// /**
	// * Find the ID for the given project URL
	// * @param projectUrl project URL to find the ID for
	// * @return ID of the project URL
	// */
	// Integer findProjectUrlId(ProjectUrl projectUrl);
	//
	// Integer replaceProjectUrl(@Param("urlId") int urlId,
	// @Param("projectId") int projectId);
	//
	// Integer replaceMerchantUrl(@Param("urlId") int urlId,
	// @Param("merchantId") int merchantId);
	//
	// /**
	// * Find all URLs in a project
	// * @param projectId project to find URLs for
	// * @return list of URLs
	// */
	// List<String> findProjectUrls(Integer projectId);
	//
	// /**
	// * Find the projects for the given client.
	// * @param organizationId ID of the client to find the projects for
	// * @return list of client projects
	// */
	// List<ClientProject> findClientProjects(Integer organizationId);
	//
	// /**
	// * Find the URLs for the given client.
	// * @param organizationId ID of the client to find the URLs for
	// * @return list of client URLs
	// */
	// List<Url> findClientUrls(Integer organizationId);
	//
	// /**
	// * Check to see if the given spreadsheet already exists
	// * @param organizationId ID of the client to check against
	// * @param projectId ID of the project to check against
	// * @param fileHash MD5 hash of the file to check against
	// * @return boolean true if duplicated
	// */
	// boolean isDuplicateSpreadsheet(@Param("organizationId") Integer
	// organizationId,
	// @Param("projectId") Integer projectId, @Param("fileHash") String
	// fileHash);
	//
	// /**
	// * Insert a new client merchant spreadsheet.
	// * @param spreaddsheet spreadsheet to be added
	// */
	// void insertClientMerchantSpreadsheet(ClientMerchantSpreadsheet
	// spreaddsheet);
	//
	// /**
	// * Update an existing client merchant spreadsheet.
	// * @param spreadsheet spreadsheet to be updated
	// */
	// void updateClientMerchantSpreadsheet(ClientMerchantSpreadsheet
	// spreadsheet);
	//
	// /**
	// * Retrieve the merchant spreadsheet for the given ID.
	// * @param id ID of the merchant spreadsheet
	// * @return merchant spreadsheet entry
	// */
	// ClientMerchantSpreadsheet findClientMerchantSpreadsheet(Integer id);
	//
	// /**
	// * Find the merchant spreadsheets for the given filter values.
	// * @param sortBy field to sort by
	// * @param sortDir direction to sort by
	// * @param organizationId client to filter by
	// * @param projectId project to filter by
	// * @param fileAction file action to filter by
	// * @return list of merchant spreadsheets that meet the filter parameters
	// */
	// List<ClientMerchantSpreadsheet> findMerchantSpreadsheets(
	// @Param("sortBy") String sortBy, @Param("sortDir") String sortDir,
	// @Param("organizationId") Integer organizationId,
	// @Param("projectId") Integer projectId, @Param("fileAction") String
	// fileAction,
	// @Param("fileSource") String fileSource);
	//
	// /**
	// * Create a new spreadsheet_stats entry in the database.
	// * @param stats spreadsheet stats to insert
	// */
	// void insertSpreadsheetStats(ClientMerchantSpreadsheetStats stats);
	//
	// /**
	// * Find the spreadsheet stats for a given ID.
	// * @param spreadsheetId the spreadsheet to find the stats for
	// * @return null if no stats found
	// */
	// ClientMerchantSpreadsheetStats findMerchantSpreadsheetStats(Integer
	// spreadsheetId);
	//
	// /**
	// * Find the stats for the given list of spreadsheets.
	// * @param spreadsheetIds spreadsheet to find the stats for
	// * @return spreadsheet stats
	// */
	// List<ClientMerchantSpreadsheetStats>
	// findManyMerchantSpreadsheetStats(List<Integer> spreadsheetIds);
	//
	// /**
	// * Find the source ID for the given spreadsheet
	// * @param spreadsheetId ID of the spreadsheet to find the source for
	// * @return ID of the source
	// */
	// Integer findMerchantSpreadsheetSourceId(Integer spreadsheetId);
	//
	// /**
	// * Create a new source entry
	// * @param spreadsheetId spreadsheet ID
	// */
	// void insertSpreadsheetSource(@Param("spreadsheetId") Integer
	// spreadsheetId);
	//
	// /**
	// * Find all of the organizations
	// * @return list of organizations
	// */
	// List<Organization> findAllOrganizations();
	//
	// /**
	// * Find the ID of the given project
	// * @param project project to find the ID for
	// * @return ID of the project
	// */
	// Integer findProjectId(ClientProject project);
	//
	// /**
	// * Add a new project
	// * @param project project
	// */
	// Integer insertOrUpdateProject(ClientProject project);
	//
	// /**
	// * Find a header identifier by alias.
	// * @param alias to search for
	// */
	// String findHeaderByAlias(String alias);
	//
	// /**
	// * Method to do a sound metric comparison against all known aliases and
	// * return the most likely matches.
	// * @param alias alias to attempt to match
	// * @param excludeHeaders headers to exclude from match
	// * @return returns a map with keys of aliases and values of headers
	// */
	// String findSimilarHeader(@Param("alias") String alias,
	// @Param("exclude") Collection<String> excludeHeaders);
	//
	// /**
	// * Inserts a new alias that maps to a header into the database.
	// * @param clientName name of the client that is the source of the CSV data
	// being uploaded
	// * @param alias alias to map to a header
	// * @param header header that alias maps to
	// */
	// void insertAliasHeaderMapping(@Param("client_name") String clientName,
	// @Param("alias") String alias, @Param("header") String header);
	//
	// /**
	// * Find the URL with the given ID.
	// *
	// * @param id The PK ID of the URL
	// * @return The URL
	// */
	// Url findUrl(@Param("id") int id);
}
