import java.lang.reflect.InvocationTargetException;

import com.works.work3.DeleteSymbolsClass;

public class Work6 {

	public static void main(String[] args) {
		DeleteSymbolsClass obj = new DeleteSymbolsClass();
		obj.setString("Hi, I'm Dima!");
		obj.deleteSymbols();
		CustomSerializer serializer = new CustomSerializer();
		try {
			serializer.Serialize(obj);
			System.out.println("Серіалізований об'єкт:");
			System.out.println(serializer.GetSerializedString());
			System.out.println("Десеріалізація об'єкта...");
			DeleteSymbolsClass object = (DeleteSymbolsClass)serializer.Deserialize();
			System.out.println("Визов функції повернення відредагованної строки десеріалізованного об'єкта");
			System.out.println(object.getEditedString());
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException | InstantiationException
				| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
