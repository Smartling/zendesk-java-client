package org.zendesk.client.v2;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.zendesk.client.v2.model.Audit;
import org.zendesk.client.v2.model.Collaborator;
import org.zendesk.client.v2.model.Comment;
import org.zendesk.client.v2.model.Field;
import org.zendesk.client.v2.model.Group;
import org.zendesk.client.v2.model.Identity;
import org.zendesk.client.v2.model.JobStatus;
import org.zendesk.client.v2.model.Locale;
import org.zendesk.client.v2.model.Organization;
import org.zendesk.client.v2.model.Request;
import org.zendesk.client.v2.model.Status;
import org.zendesk.client.v2.model.Ticket;
import org.zendesk.client.v2.model.TicketForm;
import org.zendesk.client.v2.model.User;
import org.zendesk.client.v2.model.events.Event;
import org.zendesk.client.v2.model.hc.Article;
import org.zendesk.client.v2.model.hc.Category;
import org.zendesk.client.v2.model.hc.DynamicContentItem;
import org.zendesk.client.v2.model.hc.Section;
import org.zendesk.client.v2.model.hc.Variant;
import org.zendesk.client.v2.model.targets.Target;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.HashMap;
import java.util.Properties;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.anyOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeNoException;
import static org.junit.Assume.assumeNotNull;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;
import static org.junit.Assert.assertFalse;

/**
 * @author stephenc
 * @since 04/04/2013 13:57
 */
public class RealSmokeTest {

    private static Properties config;

    private Long sectionId;
    private Long categoryId;
    private Long dynamicContentItemId;
    private Long variantId;
    private String queryString;

    private Zendesk instance;

    @BeforeClass
    public static void loadConfig() {
        config = ZendeskConfig.load();
        assumeThat("We have a configuration", config, notNullValue());
        assertThat("Configuration has an url", config.getProperty("url"), notNullValue());
    }

    public void assumeHaveToken() {
        assumeThat("We have a username", config.getProperty("username"), notNullValue());
        assumeThat("We have a token", config.getProperty("token"), notNullValue());
    }

    public void assumeHavePassword() {
        assumeThat("We have a username", config.getProperty("username"), notNullValue());
        assumeThat("We have a password", config.getProperty("password"), notNullValue());
    }

    public void assumeHaveTokenOrPassword() {
        assumeThat("We have a username", config.getProperty("username"), notNullValue());
        assumeThat("We have a token or password", config.getProperty("token") != null || config.getProperty("password") != null, is(
                true));
    }

    @Before
    public void init() throws Exception {
        sectionId = parseLongOrNull(config.getProperty("sectionId"));
        categoryId = parseLongOrNull(config.getProperty("categoryId"));
        dynamicContentItemId = parseLongOrNull(config.getProperty("dynamicContentItemId"));
        variantId = parseLongOrNull(config.getProperty("variantId"));
        queryString = config.getProperty("queryString");

        createClientWithTokenOrPassword();
    }

    private static Long parseLongOrNull(final String string)
    {
        return string != null ? Long.valueOf(string) : null;
    }

    @After
    public void closeClient() {
        if (instance != null) {
            instance.close();
        }
        instance = null;
    }

    @Test
    public void createClientWithToken() throws Exception {
        assumeHaveToken();
        instance = new Zendesk.Builder(config.getProperty("url"))
                .setUsername(config.getProperty("username"))
                .setToken(config.getProperty("token"))
                .build();
    }

    @Test
    public void createClientWithTokenOrPassword() throws Exception {
        assumeHaveTokenOrPassword();
        final Zendesk.Builder builder = new Zendesk.Builder(config.getProperty("url"))
                .setUsername(config.getProperty("username"));
        if (config.getProperty("token") != null) {
            builder.setToken(config.getProperty("token"));
        } else if (config.getProperty("password") != null) {
            builder.setPassword(config.getProperty("password"));
        }
        instance = builder.build();
    }

    @Test
    public void getTicket() throws Exception {
        Ticket ticket = instance.getTicket(1);
        assertThat(ticket, notNullValue());
    }

    @Test
    @Ignore("Needs specfic ticket form instance")
    public void getTicketForm() throws Exception {
        TicketForm ticketForm = instance.getTicketForm(27562);
        assertThat(ticketForm, notNullValue());
        assertTrue(ticketForm.isEndUserVisible());
    }

    @Test
    @Ignore
    public void getTicketForms() throws Exception {
        Iterable<TicketForm> ticketForms = instance.getTicketForms();
        assertTrue(ticketForms.iterator().hasNext());
        for (TicketForm ticketForm : ticketForms) {
            assertThat(ticketForm, notNullValue());
        }
    }

    @Test
    @Ignore("Needs specfic ticket form instance")
    public void getTicketFieldsOnForm() throws Exception {
        TicketForm ticketForm = instance.getTicketForm(27562);
        for (Integer id : ticketForm.getTicketFieldIds()) {
            Field f = instance.getTicketField(id);
            assertNotNull(f);
        }
        assertThat(ticketForm, notNullValue());
        assertTrue(ticketForm.isEndUserVisible());
    }

    @Test
    public void getTargets() throws Exception {
        Long firstTargetId = null;
        for (Target target : instance.getTargets()) {
            assertNotNull(target);
            if (firstTargetId != null) {
                assertNotEquals(firstTargetId, target.getId()); // check for infinite loop
            } else {
                firstTargetId = target.getId();
            }
        }
    }

    @Test
    @Ignore("Needs test data setup correctly")
    public void getTicketsPagesRequests() throws Exception {
        int count = 0;
        for (Ticket t : instance.getTickets()) {
            assertThat(t.getSubject(), notNullValue());
            if (++count > 150) {
                break;
            }
        }
        assertThat(count, is(151));
    }

    @Test
    @Ignore("Needs test data setup correctly")
    public void getRecentTickets() throws Exception {
        int count = 0;
        for (Ticket t : instance.getRecentTickets()) {
            assertThat(t.getSubject(), notNullValue());
            if (++count > 150) {
                break;
            }
        }
        assertThat(count, is(151));
    }

    @Test
    @Ignore
    public void getTicketsById() throws Exception {
        long count = 1;
        for (Ticket t : instance.getTickets(1, 6, 11)) {
            assertThat(t.getSubject(), notNullValue());
            assertThat(t.getId(), is(count));
            count += 5;
        }
        assertThat(count, is(16L));
    }

    @Test
    public void getTicketsIncrementally() throws Exception {
        int count = 0;
        for (Ticket t : instance.getTicketsIncrementally(new Date(0L))) {
            assertThat(t.getId(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void getTicketAudits() throws Exception {
        for (Audit a : instance.getTicketAudits(1L)) {
            assertThat(a, notNullValue());
            assertThat(a.getEvents(), not(Collections.<Event>emptyList()));
        }
    }

    @Test
    public void getTicketFields() throws Exception {
        int count = 0;
        for (Field f : instance.getTicketFields()) {
            assertThat(f, notNullValue());
            assertThat(f.getId(), notNullValue());
            assertThat(f.getType(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void createClientWithPassword() throws Exception {
        assumeHavePassword();
        instance = new Zendesk.Builder(config.getProperty("url"))
                .setUsername(config.getProperty("username"))
                .setPassword(config.getProperty("password"))
                .build();
        Ticket t = instance.getTicket(1);
        assertThat(t, notNullValue());
        System.out.println(t);
    }

    @Test
    public void createAnonymousClient() {
        instance = new Zendesk.Builder(config.getProperty("url"))
                .build();
    }

    @Test
    @Ignore("Don't spam zendesk")
    public void createDeleteTicket() throws Exception {
        assumeThat("Must have a requester email", config.getProperty("requester.email"), notNullValue());
        Ticket t = new Ticket(
                new Ticket.Requester(config.getProperty("requester.name"), config.getProperty("requester.email")),
                "This is a test", new Comment("Please ignore this ticket"));
        t.setCollaborators(Arrays.asList(new Collaborator("Bob Example", "bob@example.org"), new Collaborator("Alice Example", "alice@example.org")));
        Ticket ticket = instance.createTicket(t);
        System.out.println(ticket.getId() + " -> " + ticket.getUrl());
        assertThat(ticket.getId(), notNullValue());
        try {
            Ticket t2 = instance.getTicket(ticket.getId());
            assertThat(t2, notNullValue());
            assertThat(t2.getId(), is(ticket.getId()));

            List<User> ticketCollaborators = instance.getTicketCollaborators(ticket.getId());
            assertThat("Collaborators", ticketCollaborators.size(), is(2));
            assertThat("First Collaborator", ticketCollaborators.get(0).getEmail(), anyOf(is("alice@example.org"), is("bob@example.org")));
        } finally {
            instance.deleteTicket(ticket.getId());
        }
        assertThat(ticket.getSubject(), is(t.getSubject()));
        assertThat(ticket.getRequester(), nullValue());
        assertThat(ticket.getRequesterId(), notNullValue());
        assertThat(ticket.getDescription(), is(t.getComment().getBody()));
        assertThat("Collaborators", ticket.getCollaboratorIds().size(), is(2));
        assertThat(instance.getTicket(ticket.getId()), nullValue());
    }

    @Test
    @Ignore("Don't spam zendesk")
    public void createSolveTickets() throws Exception {
        assumeThat("Must have a requester email", config.getProperty("requester.email"), notNullValue());
        Ticket ticket;
        long firstId = Long.MAX_VALUE;
        do {
            Ticket t = new Ticket(
                    new Ticket.Requester(config.getProperty("requester.name"), config.getProperty("requester.email")),
                    "This is a test " + UUID.randomUUID().toString(), new Comment("Please ignore this ticket"));
            ticket = instance.createTicket(t);
            System.out.println(ticket.getId() + " -> " + ticket.getUrl());
            assertThat(ticket.getId(), notNullValue());
            Ticket t2 = instance.getTicket(ticket.getId());
            assertThat(t2, notNullValue());
            assertThat(t2.getId(), is(ticket.getId()));
            t2.setAssigneeId(instance.getCurrentUser().getId());
            t2.setStatus(Status.CLOSED);
            instance.updateTicket(t2);
            assertThat(ticket.getSubject(), is(t.getSubject()));
            assertThat(ticket.getRequester(), nullValue());
            assertThat(ticket.getRequesterId(), notNullValue());
            assertThat(ticket.getDescription(), is(t.getComment().getBody()));
            assertThat(instance.getTicket(ticket.getId()), notNullValue());
            firstId = Math.min(ticket.getId(), firstId);
        } while (ticket.getId() < firstId + 200L); // seed enough data for the paging tests
    }

    @Test
    public void lookupUserByEmail() throws Exception {
        String requesterEmail = config.getProperty("requester.email");
        assumeThat("Must have a requester email", requesterEmail, notNullValue());
        for (User user : instance.lookupUserByEmail(requesterEmail)) {
            assertThat(user.getEmail(), is(requesterEmail));
        }
    }

    @Test
    public void searchUserByEmail() throws Exception {
        String requesterEmail = config.getProperty("requester.email");
        assumeThat("Must have a requester email", requesterEmail, notNullValue());
        for (User user : instance.getSearchResults(User.class, "requester:" + requesterEmail)) {
            assertThat(user.getEmail(), is(requesterEmail));
        }
    }

    @Test
    public void lookupUserIdentities() throws Exception {
        User user = instance.getCurrentUser();
        for (Identity i : instance.getUserIdentities(user)) {
            assertThat(i.getId(), notNullValue());
            Identity j = instance.getUserIdentity(user, i);
            assertThat(j.getId(), is(i.getId()));
            assertThat(j.getType(), is(i.getType()));
            assertThat(j.getValue(), is(i.getValue()));
        }
    }

    @Test
    public void getUserRequests() throws Exception {
        User user = instance.getCurrentUser();
        int count = 5;
        for (Request r : instance.getUserRequests(user)) {
            assertThat(r.getId(), notNullValue());
            System.out.println(r.getSubject());
            for (Comment c : instance.getRequestComments(r)) {
                assertThat(c.getId(), notNullValue());
                System.out.println("  " + c.getBody());
            }
            if (--count < 0) {
                break;
            }
        }
    }

    @Test
    public void getUsers() throws Exception {
        int count = 0;
        for (User u : instance.getUsers()) {
            assertThat(u.getName(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void getUsersIncrementally() throws Exception {
        int count = 0;
        for (User u : instance.getUsersIncrementally(new Date(0L))) {
            assertThat(u.getName(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void getOrganizations() throws Exception {
        int count = 0;
        for (Organization t : instance.getOrganizations()) {
            assertThat(t.getName(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void getOrganizationsIncrementally() throws Exception {
        int count = 0;
        for (Organization t : instance.getOrganizationsIncrementally(new Date(0L))) {
            assertThat(t.getName(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void createOrganization() throws Exception {
        // Clean up to avoid conflicts
        for (Organization t : instance.getOrganizations()) {
            if ("testorg" .equals(t.getExternalId())) {
                instance.deleteOrganization(t);
            }
        }

        Organization org = new Organization();
        org.setExternalId("testorg");
        org.setName("Test Organization");
        Organization result = instance.createOrganization(org);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals("Test Organization", result.getName());
        assertEquals("testorg", result.getExternalId());
        instance.deleteOrganization(result);
    }

    @Test(timeout = 10000)
    public void createOrganizations() throws Exception {
        // Clean up to avoid conflicts
        for (Organization t : instance.getOrganizations()) {
            if ("testorg1" .equals(t.getExternalId()) || "testorg2" .equals(t.getExternalId())) {
                instance.deleteOrganization(t);
            }
        }

        Organization org1 = new Organization();
        org1.setExternalId("testorg1");
        org1.setName("Test Organization 1");

        Organization org2 = new Organization();
        org2.setExternalId("testorg2");
        org2.setName("Test Organization 2");

        JobStatus<Organization> result = instance.createOrganizations(org1, org2);
        assertNotNull(result);
        assertNotNull(result.getId());
        assertNotNull(result.getStatus());

        while (result.getStatus() != JobStatus.JobStatusEnum.completed) {
            result = instance.getJobStatus(result);
            assertNotNull(result);
            assertNotNull(result.getId());
            assertNotNull(result.getStatus());
        }

        List<Organization> resultOrgs = result.getResults();

        assertEquals(2, resultOrgs.size());
        for (Organization org : resultOrgs) {
            assertNotNull(org.getId());
            instance.deleteOrganization(org);
        }
    }

    @Test(timeout = 10000)
    public void bulkCreateMultipleJobs() throws Exception {
        List<Organization> orgs = new ArrayList<Organization>(4);
        for (int i = 1; i <= 5; i++) {
            Organization org = new Organization();
            org.setExternalId("testorg" + i);
            org.setName("Test Organization " + i);
            orgs.add(org);
        }

        // Clean up to avoid conflicts
        for (Organization t : instance.getOrganizations()) {
            for (Organization org : orgs) {
                if (org.getExternalId().equals(t.getExternalId())) {
                    instance.deleteOrganization(t);
                }
            }
        }


        JobStatus result1 = instance.createOrganizations(orgs.subList(0, 2));
        JobStatus result2 = instance.createOrganizations(orgs.subList(2, 5));

        while (result1.getStatus() != JobStatus.JobStatusEnum.completed || result2.getStatus() != JobStatus.JobStatusEnum.completed) {
            List<JobStatus<HashMap<String, Object>>> results = instance.getJobStatuses(Arrays.asList(result1, result2));
            result1 = results.get(0);
            result2 = results.get(1);
            assertNotNull(result1);
            assertNotNull(result1.getId());
            assertNotNull(result2);
            assertNotNull(result2.getId());
        }

        List<HashMap> resultOrgs1 = result1.getResults();
        assertEquals(2, resultOrgs1.size());
        List<HashMap> resultOrgs2 = result2.getResults();
        assertEquals(3, resultOrgs2.size());

        for (HashMap org : resultOrgs1) {
            assertNotNull(org.get("id"));
            instance.deleteOrganization(((Number) org.get("id")).longValue());
        }

        for (HashMap org : resultOrgs2) {
            assertNotNull(org.get("id"));
            instance.deleteOrganization(((Number) org.get("id")).longValue());
        }
    }

    @Test
    public void getGroups() throws Exception {
        int count = 0;
        for (Group t : instance.getGroups()) {
            assertThat(t.getName(), notNullValue());
            if (++count > 10) {
                break;
            }
        }
    }

    @Test
    public void getArticles() throws Exception {
        int count = 0;
        for (Article t : instance.getArticles()) {
            assertThat(t.getTitle(), notNullValue());
            if (++count > 40) {  // Check enough to pull 2 result pages
                break;
            }
        }
    }

    @Test
    public void shouldReturnArticlesForSingleLocale() throws Exception {
        Iterable<Article> articlesForSpecificLocale = instance.getArticles(1, 20, "en-us", "updated_at", "asc");

        for (Article article : articlesForSpecificLocale) {
            assertTrue(article.getLocale().equals("en-us"));
        }
    }

    @Test
    public void sizeShouldShouldBeNotMoreThanPerPageSize() throws Exception {
        Iterable<Article> articlesForSpecificLocale = instance.getArticles(1, 20, "en-us", "updated_at", "asc");

        List<Article> articleList = getList(articlesForSpecificLocale);
        assertTrue(articleList.size() <= 20);
    }

    @Test
    public void shouldBeSortedByTitleInAsc() throws Exception {
        Iterable<Article> articlesForSpecificLocale = instance.getArticles(1, 20, "en-us", "title", "asc");

        String previous = null;

        for (Article article : articlesForSpecificLocale) {
            if (previous == null) {
                previous = article.getTitle();
            } else {
                String current = article.getTitle();

                assertTrue(current.compareTo(previous) >= 0);

                previous = current;
            }

        }
    }

    @Test
    public void shouldBeSortedByUpdatedAtInAsc() throws Exception {
        Iterable<Article> articlesForSpecificLocale = instance.getArticles(1, 20, "en-us", "updated_at", "asc");

        Date previous = null;

        for (Article article : articlesForSpecificLocale) {
            if (previous == null) {
                previous = article.getUpdatedAt();
            } else {
                Date current = article.getUpdatedAt();

                assertTrue(current.after(previous) || current.equals(previous));

                previous = current;
            }

        }
    }

    @Test
    public void shouldBeSortedByTitleInDesc() throws Exception {
        Iterable<Article> articlesForSpecificLocale = instance.getArticles(1, 20, "en-us", "title", "desc");

        String previous = null;

        for (Article article : articlesForSpecificLocale) {
            if (previous == null) {
                previous = article.getTitle();
            } else {
                String current = article.getTitle();

                assertTrue(current.compareTo(previous) <= 0);

                previous = current;
            }

        }
    }

    @Test
    public void shouldLoadAllLocales() throws Exception {
        List<Locale> locales = instance.getLocales();

        assertTrue(locales.size() >= 1);
        assertNotNull(locales.get(0).getId());
        assertNotNull(locales.get(0).getLocale());
        assertNotNull(locales.get(0).getName());
    }

    @Test
    public void shouldSearchArticleByCategory() throws Exception {
        assumeNotNull("Category ID is required to run this test", categoryId);

        Iterable<Article> articleFromSearch = instance.getArticlesByQueryAndCategory(1, 10, "en-us", null, categoryId);

        List<Article> result = getList(articleFromSearch);
        assertEquals(Integer.parseInt(config.getProperty("expected.articles.by.category")), result.size());
    }

    @Test
    public void shouldSearchArticleBySection() throws Exception {
        assumeNotNull("Section ID is required to run this test", sectionId);

        Iterable<Article> articleFromSearch = instance.getArticlesByQueryAndSection(1, 10, "en-us", null, sectionId);

        List<Article> result = getList(articleFromSearch);
        assertEquals(Integer.parseInt(config.getProperty("expected.articles.by.section")), result.size());
    }

    @Test
    public void shouldSearchArticleByQuery() throws Exception {
        assumeNotNull("Search query is required to run this test", queryString);

        Iterable<Article> articleFromSearch = instance.getArticlesByQuery(1, 10, "en-us", queryString);

        List<Article> result = getList(articleFromSearch);
        assertEquals(Integer.parseInt(config.getProperty("expected.articles.by.query")), result.size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowExceptionIfQueryNotProvided() throws Exception {
        instance.getArticlesByQuery(1, 10, "en-us", null);
    }

    @Test
    public void shouldSearchArticleByQueryInOneCategory() throws Exception {
        assumeNotNull("Search query is required to run this test", queryString);
        assumeNotNull("Category ID is required to run this test", categoryId);

        Iterable<Article> articleFromSearch = instance.getArticlesByQueryAndCategory(1, 10, "en-us", queryString, categoryId);

        List<Article> result = getList(articleFromSearch);
        assertEquals(Integer.parseInt(config.getProperty("expected.articles.by.query.and.category")), result.size());
    }

    @Test
    public void shouldReturnEmptyIterableIfQueryNotProvidedAndNonExistentCategoryId() throws Exception {
        Iterable<Article> articlesByQueryAndCategory = instance.getArticlesByQueryAndCategory(1, 10, "en-us", null, 0);

        assertTrue(getList(articlesByQueryAndCategory).isEmpty());
    }

    @Test
    public void shouldSearchArticleByQueryInOneSection() throws Exception {
        assumeNotNull("Search query is required to run this test", queryString);
        assumeNotNull("Section ID is required to run this test", sectionId);

        Iterable<Article> articleFromSearch = instance.getArticlesByQueryAndSection(1, 10, "en-us", queryString, sectionId);

        List<Article> result = getList(articleFromSearch);
        assertEquals(Integer.parseInt(config.getProperty("expected.articles.by.query.and.section")), result.size());
    }

    @Test
    public void shouldReturnSectionsByCategory() throws Exception {
        assumeNotNull("Category ID is required to run this test", categoryId);

        Iterable<Section> sectionsByCategory = instance.getSectionsByCategory(categoryId, 1, 10, "updated_at", "desc");

        List<Section> result = getList(sectionsByCategory);
        assertEquals(Integer.parseInt(config.getProperty("expected.sections.by.category")), result.size());
    }

    @Test(expected = ZendeskResponseException.class)
    public void shouldThrowExceptionWhenSearchByNonExistingCategoryId() throws Exception {
        instance.getSectionsByCategory(0, 1, 10, "updated_at", "desc");
    }

    @Test
    public void shouldReturnSectionsByLocaleAndCategory() throws Exception {
        assumeNotNull("Category ID is required to run this test", categoryId);

        Iterable<Section> sectionsByCategory = instance.getSectionsByCategory(categoryId, "en-us", 1, 10, "updated_at", "desc");

        List<Section> result = getList(sectionsByCategory);
        assertEquals(Integer.parseInt(config.getProperty("expected.sections.by.category")), result.size());
    }

    @Test
    public void shouldReturnEmptyIterableIfQueryNotProvidedAndNonExistentSectionId() throws Exception {
        Iterable<Article> articlesByQueryAndCategory = instance.getArticlesByQueryAndSection(1, 10, "en-us", null, 0);

        assertTrue(getList(articlesByQueryAndCategory).isEmpty());
    }

    @Test
    public void shouldGetArticleByLocaleAndId() throws Exception
    {
        Article existingArticle = getRandomArticle();

        Article loadedArticle = instance.getArticle("en-us", existingArticle.getId().intValue());

        assertEquals(loadedArticle.getId(), existingArticle.getId());
    }

    @Test
    public void shouldGetCategoryByLocaleAndId() throws Exception
    {
        Category existingCategory = getRandomCategory();

        Category loadedCategory = instance.getCategory("en-us", existingCategory.getId().intValue());

        assertEquals(loadedCategory.getId(), existingCategory.getId());
    }

    @Test
    public void shouldGetSectionByLocaleAndId() throws Exception
    {
        Section existingSection = getRandomSection();

        Section loadedCategory = instance.getSection("en-us",existingSection.getId().intValue());

        assertEquals(loadedCategory.getId(), existingSection.getId());
    }

    @Test
    public void shouldReturnNotEmptyVariantsCollection() throws Exception
    {
        assumeNotNull("DynamicContentItemId ID is required to run this test", dynamicContentItemId);

        Iterable<Variant> variants = instance.getVariants(dynamicContentItemId);
        assertFalse(getList(variants).isEmpty());
    }

    @Test
    public void shouldReturnVariantById() throws Exception {
        assumeNotNull("DynamicContentItemId is required to run this test", dynamicContentItemId);
        assumeNotNull("variantId is required to run this test", variantId);

        Variant variantById = instance.getVariantById(dynamicContentItemId, variantId);

        assertEquals(variantId, variantById.getId());
    }

    @Test
    public void shouldCreateVariant() throws Exception {
        assumeNotNull("DynamicContentItemId ID is required to run this test", dynamicContentItemId);

        Iterable<Variant> variants = instance.getVariants(dynamicContentItemId);
        int initialSize = getList(variants).size();

        instance.createVariant(dynamicContentItemId, getVariant(null, 2L, "Spanish variant"));

        Iterable<Variant> resultVariants = instance.getVariants(dynamicContentItemId);
        assertEquals(initialSize + 1, getList(resultVariants).size());
    }

    @Test
    public void createdVariantShouldContainAppropriateFields() throws Exception{
        assumeNotNull("DynamicContentItemId ID is required to run this test", dynamicContentItemId);

        Variant resultVariant = instance.createVariant(dynamicContentItemId, getVariant(null, 1365L, "French variant"));

        assertTrue(resultVariant.getId()!=null);
        assertEquals(new Long(1365), resultVariant.getLocaleId());
        assertEquals("French variant", resultVariant.getContent());
    }

    @Test
    public void shouldUpdateVariant() throws Exception{
        assumeNotNull("dynamicContentItemId is required to run this test", dynamicContentItemId);
        assumeNotNull("variantId is required to run this test", variantId);

        Variant resultVariant = instance.updateVariant(dynamicContentItemId, getVariant(variantId, 1365L, "French variant updated"));

        Variant variantById = instance.getVariantById(dynamicContentItemId, resultVariant.getId());

        assertEquals("French variant updated", variantById.getContent());
    }

    @Test
    public void shouldReturnNotEmptyDynamicContentCollection() throws Exception
    {
        Iterable<DynamicContentItem> dynamicContent = instance.getDynamicContentItems(1, 10, "updated_at", "asc");
        assertFalse(getList(dynamicContent).isEmpty());
    }

    private Article getRandomArticle()
    {
        Article existingArticle = null;
        try {
            Iterable<Article> articles = instance.getArticles();
            assumeTrue("At least one article is needed for this test", articles.iterator().hasNext());

            existingArticle = articles.iterator().next();
        } catch (ZendeskException e) {
            assumeNoException("Need to be able to fetch articles to run this test", e);
        }

        return existingArticle;
    }

    private Category getRandomCategory()
    {
        Category existingCategory = null;
        try {
            Iterable<Category> categories = instance.getCategories();
            assumeTrue("At least one category is needed for this test", categories.iterator().hasNext());

            existingCategory = categories.iterator().next();
        } catch (ZendeskException e) {
            assumeNoException("Need to be able to fetch category to run this test", e);
        }

        return existingCategory;
    }

    private Section getRandomSection()
    {
        Section existingSection = null;
        try {
            Iterable<Section> sections = instance.getSections();
            assumeTrue("At least one section is needed for this test", sections.iterator().hasNext());

            existingSection = sections.iterator().next();
        } catch (ZendeskException e) {
            assumeNoException("Need to be able to fetch section to run this test", e);
        }

        return existingSection;
    }

    private Variant getVariant(Long variantId, Long localeId, String content)
    {
        Variant variant = new Variant();
        variant.setId(variantId);
        variant.setContent(content);
        variant.setActive(true);
        variant.setDefaultVariant(false);
        variant.setLocaleId(localeId);
        return variant;
    }

    private <T> List<T> getList(Iterable<T> iterable)
    {
        List<T> result = new ArrayList<>();
        for (T article : iterable) {
            result.add(article);
        }
        return result;
    }
}
