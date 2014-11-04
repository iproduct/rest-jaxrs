package org.iproduct.rest.hateoas.jaxrs.polling.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Models a polling survey with id, title and question
 * 
 * @author Trayan Iliev
 * @author IPT [http://iproduct.org]
 * 
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Poll implements Identifiable<Long>{
	Long id;
	String title;
	String question;

	/**
	 * No argument constructor
	 */
	public Poll() {
	}

	/**
	 * Full constructor
	 * 
	 * @param id
	 *            poll identifier
	 * @param title
	 *            poll title
	 * @param question
	 *            poll question
	 */
	public Poll(Long id, String title, String question) {
		this.id = id;
		this.title = title;
		this.question = question;
	}

	/**
	 * Get the poll identifier
	 * @return the poll identifier
	 */
	public Long getId() {
		return id;
	}

//	/**
//	 * Set the poll identifier
//	 * @param id
//	 *            the poll identifier to set
//	 */
//	public void setId(long id) {
//		this.id = id;
//	}

	/**
	 * Get the poll title
	 * @return the poll title
	 */
	public String getTitle() {
		return title;
	}

//	/**
//	 * Set the poll title
//	 * @param title
//	 *            the poll title to set
//	 */
//	public void setTitle(String title) {
//		this.title = title;
//	}

	/**
	 * Get the poll question
	 * @return the poll question
	 */
	public String getQuestion() {
		return question;
	}

//	/**
//	 * Set the poll question
//	 * @param question
//	 *            the poll question to set
//	 */
//	public void setQuestion(String question) {
//		this.question = question;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Poll other = (Poll) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Poll [id=" + id + ", title=" + title + ", question=" + question
				+ "]";
	}

}
