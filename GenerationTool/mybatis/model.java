package {daoPkName}.model;
import {daoPkName}.dao.*;
import java.sql.Timestamp;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class {className}  implements Serializable, DataModel{
{encapsulateFields}
    public {className} copy() {
        {className} copyObj = new {className}();
		{copyfields}
        return copyObj;
    }
	@Override
	public String toString() {
		return String.valueOf({fstringpropName}); 
	}
}
