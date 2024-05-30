import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
class Student
{
	private String name;
	private int rollno;
	private String grade;
	private String degree;
	public Student(String name,int rollno,String grade,String degree)
	{
		this.name=name;
		this.rollno=rollno;
		this.grade=grade;
		this.degree=degree;
	}
	public String getName()
	{
		return name;
	}
	public int getRollno()
	{
		return rollno;
	}
	public String getGrade()
	{
		return grade;
	}
	public String getDegree()
	{
		return degree;
	}
}
class StudentManagementSystem implements ActionListener
{
	JLabel l1,l2,l3,l4;
	JTextField t1,t2,t3;
	JButton b1,b2,b3,b4;
	static JTextArea ta1;
	JComboBox<String> degreeComboBox;
	public StudentManagementSystem()
	{
		JFrame f=new JFrame();
		f.setVisible(true);
		f.setLayout(null);
		f.setSize(500,600);
		f.setTitle("Student Management System");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		l1=new JLabel("Student Name:");
		l1.setBounds(40,30,120,25);
		f.add(l1);

		t1=new JTextField();
		t1.setBounds(185,30,200,25);
		f.add(t1);

		l2=new JLabel("Student Rollno:");
		l2.setBounds(40,70,130,25);
		f.add(l2);

		t2=new JTextField();
		t2.setBounds(185,70,200,25);
		f.add(t2);

		l3=new JLabel("Student Grade:");
		l3.setBounds(40,110,130,25);
		f.add(l3);

		t3=new JTextField();
		t3.setBounds(185,110,200,25);
		f.add(t3);

		l4=new JLabel("Student Degree:");
		l4.setBounds(40,150,150,25);
		f.add(l4);

		String[] listDegree={"B.Com","B.M.M.C","B.M.S","BSc.IT","B.A.F","B.I.M"};
		degreeComboBox=new JComboBox<>(listDegree);
		degreeComboBox.setBounds(185,150,200,25);
		f.add(degreeComboBox);

		b1=new JButton("Add Student");
		b1.setBounds(40,190,130,25);
		f.add(b1);
		b1.addActionListener(this);

		b2=new JButton("Remove Student");
		b2.setBounds(185,190,150,25);
		f.add(b2);
		b2.addActionListener(this);

		b3=new JButton("Search Student");
		b3.setBounds(40,230,130,25);
		f.add(b3);
		b3.addActionListener(this);

		b4=new JButton("Display All Student");
		b4.setBounds(185,230,150,25);
		f.add(b4);
		b4.addActionListener(this);

		ta1=new JTextArea();
		ta1.setBounds(40,270,370,270);
		f.add(ta1);
	}
	public void actionPerformed(ActionEvent ae)
	{
		try
		{
			Object source=ae.getSource();
			if(source==b1)
			{
				String name=t1.getText();
    			String rollnoText=t2.getText();
    			String grade=t3.getText();
    			String degree=(String) degreeComboBox.getSelectedItem();
				if(name.isEmpty() || rollnoText.isEmpty() || grade.isEmpty() || degree.isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Please Fill The Details!");
				}
				else
				{
					try
					{
						int rollno=Integer.parseInt(rollnoText);
                		Student stud=new Student(name,rollno,grade,degree);
                		if(checkRollnoExists(rollno)) 
                		{
                    		JOptionPane.showMessageDialog(null, "Student with the same roll number already exists!");
                		} 
                		else 
                		{
                    		addStudent(stud);
                    		JOptionPane.showMessageDialog(null, "Student Added Successfully");
                    		clearField();
                		}
                    }
                    catch(Exception e)
                    {
                    	 JOptionPane.showMessageDialog(null, "Please Enter Valid Rollno!");
                    }		
				}
			}
			if(source==b2)
			{
				String rollnoText=JOptionPane.showInputDialog(null, "Enter Student Roll Number to Remove:");
                if(rollnoText!=null && !rollnoText.isEmpty()) 
                {
                    try
                    {
                        int rollno=Integer.parseInt(rollnoText);
                        if(checkRollnoExists(rollno)) 
                        {
                            removeStudentByRollno(rollno);
                            JOptionPane.showMessageDialog(null,"Student Removed Successfully");
                        } 
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Student with the given roll number does not exist!");
                        }
                    } 
                    catch(Exception e) 
                    {
                        JOptionPane.showMessageDialog(null,"Please Enter Valid Rollno!");
                    }
                }
			}
			if(source==b3)
			{
				String rollnoText=JOptionPane.showInputDialog(null,"Enter Student Roll Number to Search:");
                if(rollnoText!=null && !rollnoText.isEmpty()) 
                {
                    try 
                    {
                        int rollno=Integer.parseInt(rollnoText);
                        if(checkRollnoExists(rollno)) 
                        {
                            searchStudentById(rollno);
                        } 
                        else 
                        {
                            JOptionPane.showMessageDialog(null,"Student with the given roll number does not exist!");
                        }
                    } 
                    catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null, "Please Enter Valid Rollno!");
                    }
                }
			}
			if(source==b4)
			{
				displayAll();
			}
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
	private void clearField()
	{
		t1.setText("");
		t2.setText("");
		t3.setText("");
		degreeComboBox.setSelectedIndex(-1);
	}
	public static void main(String[] args) {
		StudentManagementSystem system=new StudentManagementSystem();
	}
	private static void addStudent(Student stud)
	{
		try
		{
			FileWriter fw=new FileWriter("StudentDetails.txt",true);
			fw.write(stud.getName()+"\t");
			fw.write(stud.getRollno()+"\t");
			fw.write(stud.getGrade()+"\t");
			fw.write(stud.getDegree());
			fw.write("\r\n");
			fw.close();
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}		
	}
	private static void displayAll()throws IOException
	{
		String data;
		BufferedReader reader=new BufferedReader(new FileReader("StudentDetails.txt"));
		ta1.setText("Student Name   |Student Rollno |Student Grade  |Student Degree");
		ta1.append("\n------------------------------------------------------------------------------------------------");
		while((data=reader.readLine())!=null)
		{
			String[] parts=data.split("\t");
			if(parts.length==4)
			{
				String name=parts[0];
				int rollno=Integer.parseInt(parts[1]);
				String grade=parts[2];
				String degree=parts[3];
				Student stud=new Student(name,rollno,grade,degree);				
				ta1.append("\n"+stud.getName()+"\t|"+stud.getRollno()+"\t|"+stud.getGrade()+"\t|"+stud.getDegree());
			}			
		}
		reader.close();
	} 
	private static void removeStudentByRollno(int rollno)
	{
		try 
		{
        	File inputFile=new File("StudentDetails.txt");
        	File tempFile=new File("temp.txt");
       	 	BufferedReader reader=new BufferedReader(new FileReader(inputFile));
        	BufferedWriter writer=new BufferedWriter(new FileWriter(tempFile));
        	String line;
        	boolean found=false;

        	while((line=reader.readLine())!=null) 
        	{
            	String[] parts=line.split("\t");
            	if(parts.length>=2) 
            	{
            	    int existingRollno=Integer.parseInt(parts[1]);
            	    if(existingRollno!=rollno)
            	    {
            	        writer.write(line);
            	        writer.newLine();
            	    } 
            	    else 
            	    {
            	        found=true;
            	    }
            	}
        	}
        	reader.close();
        	writer.close();
	
        	if(found) 
        	{
        	    inputFile.delete();
        	    tempFile.renameTo(inputFile);
        	} 
        	else
        	{
        	    tempFile.delete();
        	}
    	} 
    	catch(Exception e) 
    	{
        	System.out.println(e.getMessage());
    	}
	}
	private static void searchStudentById(int rollno) 
	{
    try 
    {
        File file=new File("StudentDetails.txt");
        if(file.exists()) 
        {
            Scanner sc=new Scanner(file);
            boolean found=false;
            ta1.setText("Student Name   |Student Rollno |Student Grade  |Student Degree");
            ta1.append("\n------------------------------------------------------------------------------------------------");
            while(sc.hasNextLine()) 
            {
                String line=sc.nextLine();
                String[] parts=line.split("\t");
                if(parts.length==4) 
                {
                    int existingRollno=Integer.parseInt(parts[1]);
                    if(existingRollno==rollno) 
                    {
                        String name=parts[0];
                        String grade=parts[2];
                        String degree=parts[3];
                        Student stud=new Student(name,rollno,grade,degree);
                        ta1.append("\n"+stud.getName()+"\t|"+stud.getRollno()+"\t|"+stud.getGrade()+"\t|"+stud.getDegree());
                        found=true;
                    }
                }
            }
            sc.close();
            if(!found) 
            {
                JOptionPane.showMessageDialog(null,"No student found with the given roll number.");
            }
        } 
        else 
        {
            JOptionPane.showMessageDialog(null,"Student record file not found.");
        }
    } 
    catch(Exception e) 
    {
        System.out.println(e.getMessage());
    }
	}
	private static boolean checkRollnoExists(int rollno) 
	{
    	try 
    	{
    	    File file=new File("StudentDetails.txt");
    	    if(file.exists()) 
    	    {
    	        Scanner sc=new Scanner(file);
    	        while(sc.hasNextLine()) 
    	        {
    	            String line=sc.nextLine();
    	            String[] parts=line.split("\t");
    	            if(parts.length>=2) 
    	            {
    	                int existingRollno=Integer.parseInt(parts[1]);
    	                if(existingRollno==rollno) 
    	                {
    	                    sc.close();
    	                    return true;
    	                }
    	            }
    	        }
    	        sc.close();
    	    }
    	} 
    	catch(Exception e) 
    	{
    	    System.out.println(e.getMessage());
    	}
    	return false;
	}
}