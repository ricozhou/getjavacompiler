package getjavacompiler;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

//获取java编译器
public class GetJC {
	public static void main(String[] args) throws Exception {
		System.out.println(getJavaCompiler1());
		System.out.println(getJavaCompiler2());
		System.out.println(getJavaCompiler3("E:\\Java\\tools.jar"));
	}

	// 第一种，使用系统方法获取
	public static JavaCompiler getJavaCompiler1() {
		// 需要把jdk/lib下的tools.jar复制到jdk/jre/lib下
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		return compiler;
	}

	// 第二种，使用环境变量从jdk中读取
	public static JavaCompiler getJavaCompiler2() throws Exception {
		String javahome = System.getenv("JAVA_HOME");
		File file = new File(javahome + File.separator + "lib\\tools.jar");
		if (!file.exists()) {
			return null;
		}
		JavaCompiler compiler = getJavaCompilerByLocation(file);
		return compiler;
	}

	// 第三种，任意目录下tools.jar读取
	public static JavaCompiler getJavaCompiler3(String filePath) throws Exception {
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		JavaCompiler compiler = getJavaCompilerByLocation(file);
		return compiler;
	}

	// 获取编译器
	public static JavaCompiler getJavaCompilerByLocation(File f1) throws Exception {
		String p = f1.getAbsolutePath();
		URL[] urls = new URL[] { new URL("file:/" + p) };
		URLClassLoader myClassLoader1 = new URLClassLoader(urls, Thread.currentThread().getContextClassLoader());
		Class<?> myClass1 = myClassLoader1.loadClass("com.sun.tools.javac.api.JavacTool");
		JavaCompiler compiler = myClass1.asSubclass(JavaCompiler.class).asSubclass(JavaCompiler.class).newInstance();
		return compiler;
	}

}
