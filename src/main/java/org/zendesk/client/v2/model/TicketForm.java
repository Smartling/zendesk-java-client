package org.zendesk.client.v2.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketForm implements SearchResultEntity, Serializable {

    private static final long serialVersionUID = 1L;

	private Long id;
	private String name;
	@JsonProperty("raw_name")
	private String rawName;
	@JsonProperty("display_name")
	private String displayName;
	@JsonProperty("raw_display_name")
	private String rawDisplayName;
	private int position;
	private boolean active;
	@JsonProperty("end_user_visible")
	private boolean endUserVisible;
	@JsonProperty("default")
	private boolean defaultForm;
	@JsonProperty("ticket_field_ids")
	private List<Long> ticketFieldIds;
	@JsonProperty("created_at")
    private Date createdAt;
	@JsonProperty("updated_at")
    private Date updatedAt;
	@JsonProperty("end_user_conditions")
	private List<EndUserCondition> endUserConditions;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRawName() {
		return rawName;
	}
	public void setRawName(String rawName) {
		this.rawName = rawName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public String getRawDisplayName() {
		return rawDisplayName;
	}
	public void setRawDisplayName(String rawDisplayName) {
		this.rawDisplayName = rawDisplayName;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public boolean isEndUserVisible() {
		return endUserVisible;
	}
	public void setEndUserVisible(boolean endUserVisible) {
		this.endUserVisible = endUserVisible;
	}
	public boolean isDefaultForm() {
		return defaultForm;
	}
	public void setDefaultForm(boolean defaultForm) {
		this.defaultForm = defaultForm;
	}
	public List<Long> getTicketFieldIds() {
		return ticketFieldIds;
	}
	public void setTicketFieldIds(List<Long> ticketFieldIds) {
		this.ticketFieldIds = ticketFieldIds;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public Date getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public List<EndUserCondition> getEndUserConditions() {
		return endUserConditions;
	}

	public void setEndUserConditions(List<EndUserCondition> endUserConditions) {
		this.endUserConditions = endUserConditions;
	}

	public static class EndUserCondition implements Serializable {
		private static final long serialVersionUID = 1L;
		@JsonProperty("parent_field_id")
		private String parentFieldId;
		private String value;
		@JsonProperty("child_fields")
		private List<ChildField> childFields;

		public String getParentFieldId() {
			return parentFieldId;
		}

		public void setParentFieldId(String parentFieldId) {
			this.parentFieldId = parentFieldId;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public List<ChildField> getChildFields() {
			return childFields;
		}

		public void setChildFields(List<ChildField> childFields) {
			this.childFields = childFields;
		}
	}

	public static class ChildField implements Serializable {
		private static final long serialVersionUID = 1L;
		private String id;
		private boolean isRequired;

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public boolean isRequired() {
			return isRequired;
		}

		public void setRequired(boolean required) {
			isRequired = required;
		}
	}
}
