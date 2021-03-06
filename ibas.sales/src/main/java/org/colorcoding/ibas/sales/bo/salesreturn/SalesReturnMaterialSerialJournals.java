package org.colorcoding.ibas.sales.bo.salesreturn;

import org.colorcoding.ibas.bobas.bo.BusinessObjects;
import org.colorcoding.ibas.bobas.common.*;
import org.colorcoding.ibas.materials.bo.materialserial.IMaterialSerialJournal;
import org.colorcoding.ibas.materials.bo.materialserial.MaterialSerialJournal;
import org.colorcoding.ibas.sales.MyConfiguration;

import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import java.beans.PropertyChangeEvent;

/**
 * 销售退货-序列 集合
 */
@XmlType(name = SalesReturnMaterialSerialJournals.BUSINESS_OBJECT_NAME, namespace = MyConfiguration.NAMESPACE_BO)
@XmlSeeAlso({ MaterialSerialJournal.class })
public class SalesReturnMaterialSerialJournals extends BusinessObjects<IMaterialSerialJournal,ISalesReturnItem> implements ISalesReturnMaterialSerialJournals{
    /**
     * 业务对象名称
     */
    public static final String BUSINESS_OBJECT_NAME = "SalesReturnMaterialSerialJournals";

    /**
     * 序列化版本标记
     */
    private static final long serialVersionUID = 7759763557795210418L;

    public SalesReturnMaterialSerialJournals(){super();}

    /**
     * 构造方法
     * @param parent 父项对象
     */
    public SalesReturnMaterialSerialJournals(ISalesReturnItem parent) {
        super(parent);
    }

    @Override
    public IMaterialSerialJournal create() {
        IMaterialSerialJournal item = new MaterialSerialJournal();
        if (this.add(item)) {
            return item;
        }
        return null;
    }

    /**
     * 元素类型
     */
    public Class<?> getElementType() {
        return MaterialSerialJournal.class;
    }

    @Override
    protected void afterAddItem(IMaterialSerialJournal item) {
        super.afterAddItem(item);
        // TODO 设置关联值
        item.setBaseDocumentType(this.getParent().getObjectCode());
        item.setBaseDocumentEntry(this.getParent().getDocEntry());
        item.setBaseDocumentLineId(this.getParent().getLineId());
    }

    @Override
    public ICriteria getElementCriteria() {
        ICriteria criteria = new Criteria();
        ICondition condition = criteria.getConditions().create();
        condition.setAlias("BaseType");
        condition.setOperation(ConditionOperation.EQUAL);
        condition.setValue(this.getParent().getObjectCode());
        condition = criteria.getConditions().create();
        condition.setAlias("BaseEntry");
        condition.setOperation(ConditionOperation.EQUAL);
        condition.setValue(this.getParent().getDocEntry());
        condition.setRelationship(ConditionRelationship.AND);
        return criteria;
    }

    @Override
    public void onParentPropertyChanged(PropertyChangeEvent evt) {
        super.onParentPropertyChanged(evt);
        // TODO 设置关联值
        if(evt.getPropertyName().equalsIgnoreCase(SalesReturnItem.MASTER_PRIMARY_KEY_NAME)){
            for(IMaterialSerialJournal item: this){
                item.setBaseDocumentType(this.getParent().getObjectCode());
                item.setBaseDocumentEntry(this.getParent().getDocEntry());
                item.setBaseDocumentLineId(this.getParent().getLineId());
            }
        }
    }
}
