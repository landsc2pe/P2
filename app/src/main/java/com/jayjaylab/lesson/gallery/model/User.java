
package com.jayjaylab.lesson.gallery.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "total_count",
    "incomplete_results",
    "items"
})
public class User {

    @JsonProperty("total_count")
    private int totalCount;
    @JsonProperty("incomplete_results")
    private boolean incompleteResults;
    @JsonProperty("items")
    private List<Item> items = new ArrayList<Item>();

    @Override
    public String toString() {
        return "User{" +
                "totalCount=" + totalCount +
                ", incompleteResults=" + incompleteResults +
                ", items=" + items +
                '}';
    }

    /**
     * 
     * @return
     *     The totalCount
     */
    @JsonProperty("total_count")
    public int getTotalCount() {
        return totalCount;
    }

    /**
     * 
     * @param totalCount
     *     The total_count
     */
    @JsonProperty("total_count")
    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 
     * @return
     *     The incompleteResults
     */
    @JsonProperty("incomplete_results")
    public boolean isIncompleteResults() {
        return incompleteResults;
    }

    /**
     * 
     * @param incompleteResults
     *     The incomplete_results
     */
    @JsonProperty("incomplete_results")
    public void setIncompleteResults(boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    /**
     * 
     * @return
     *     The items
     */
    @JsonProperty("items")
    public List<Item> getItems() {
        return items;
    }

    /**
     * 
     * @param items
     *     The items
     */
    @JsonProperty("items")
    public void setItems(List<Item> items) {
        this.items = items;
    }

}
