/*
 * Created on Feb 13, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.wb.stars.common.simple;

/**
 * @author Bojja_Kishore
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

//import java.util.LinkedList;
import java.io.Serializable;

public class MailKey implements Serializable 
{

/**
	 * 
	 */
	private static final long serialVersionUID = 1012413508637689000L;
private long OrderHeaderId;
private long lOrderId;
private long lProductId;
private String DisplaycontractNumber = "";
private long systemOrderTypeId;
private long FieldOrderId;
private long customerId;
private String CallLetters;
private String ProductName;
private int iMailFlag;
private String strMailType="";
private long productTypeId=-1;
//CQ 16668
private String primaryCallLetters="";
private long originalCustomerId=0;
private long sourceOrderDetailsId=0;
private long sourceOrderHeaderId=0;

/**
 * @return
 */
public String getCallLetters()
{
	return this.CallLetters;
}

/**
 * @return
 */
public String getDisplaycontractNumber()
{
	return this.DisplaycontractNumber;
}

/**
 * @return
 */
public long getOrderHeaderId()
{
	return this.OrderHeaderId;
}

/**
 * @return
 */
public long getSystemOrderTypeId()
{
	return this.systemOrderTypeId;
}

/**
 * @param string
 */
public void setCallLetters(String CallLetters)
{
	this.CallLetters = CallLetters;
}

/**
 * @param string
 */
public void setDisplaycontractNumber(String DisplaycontractNumber)
{
	this.DisplaycontractNumber = DisplaycontractNumber;
}

/**
 * @param l
 */
public void setOrderHeaderId(long OrderHeaderId)
{
	this.OrderHeaderId = OrderHeaderId;
}

/**
 * @param l
 */
public void setSystemOrderTypeId(long systemOrderTypeId)
{
	this.systemOrderTypeId = systemOrderTypeId;
}

/**
 * @return
 */
public long getFieldOrderId()
{
	return this.FieldOrderId;
}

/**
 * @param l
 */
public void setFieldOrderId(long FieldOrderId)
{
	this.FieldOrderId = FieldOrderId;
}

/**
 * @return
 */
public long getCustomerId()
{
	return this.customerId;
}

/**
 * @param l
 */
public void setCustomerId(long customerId)
{
	this.customerId = customerId;
}

/**
 * @return
 */
public String getProductName()
{
	return this.ProductName;
}

/**
 * @param string
 */
public void setProductName(String ProductName)
{
	this.ProductName = ProductName;
}

/**
 * @return
 */
public long getOrderId()
{
	return this.lOrderId;
}

/**
 * @return
 */
public long getProductId()
{
	return this.lProductId;
}

/**
 * @param l
 */
public void setOrderId(long lOrderId)
{
	this.lOrderId = lOrderId;
}

/**
 * @param l
 */
public void setProductId(long lProductId)
{
	this.lProductId = lProductId;
}

/**
 * @return
 */
public int getMailFlag()
{
	return this.iMailFlag;
}

/**
 * @param i
 */
public void setMailFlag(int iMailFlag)
{
	this.iMailFlag = iMailFlag;
}

/**
 * @return
 */
public String getMailType()
{
	return this.strMailType;
}

/**
 * @param string
 */
public void setMailType(String strMailType)
{
	this.strMailType = strMailType;
}

/**
 * @return
 */
public long getProductTypeId()
{
	return productTypeId;
}

/**
 * @param l
 */
public void setProductTypeId(long productTypeId)
{
	this.productTypeId = productTypeId;
}

/**
 * @return
 */
public String getPrimaryCallLetters()
{
	return this.primaryCallLetters;
}

/**
 * @param string
 */
public void setPrimaryCallLetters(String primaryCallLetters)
{
	this.primaryCallLetters = primaryCallLetters;
}

/**
 * @return
 */
public long getSourceOrderDetailsId()
{
	return this.sourceOrderDetailsId;
}

/**
 * @param l
 */
public void setSourceOrderDetailsId(long sourceOrderDetailsId)
{
	this.sourceOrderDetailsId = sourceOrderDetailsId;
}

/**
 * @return
 */
public long getOriginalCustomerId()
{
	return this.originalCustomerId;
}

/**
 * @param l
 */
public void setOriginalCustomerId(long originalCustomerId)
{
	this.originalCustomerId = originalCustomerId;
}

/**
 * @return
 */
public long getSourceOrderHeaderId()
{
	return this.sourceOrderHeaderId;
}

/**
 * @param l
 */
public void setSourceOrderHeaderId(long sourceOrderHeaderId)
{
	this.sourceOrderHeaderId = sourceOrderHeaderId;
}

}
