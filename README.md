Training: computer-database    
=========================== 

# Content
This training material holds a sequence of steps and features to implement in a Computer Database webapp.  
Here is the macro-planning and timeline of all milestones:  
 * t0    - Start of the project
 * t0+3  - Base Architecture, CLI (Add / Edit features), Logging

# Installation

## 1. Database
Create a local **MySQL** server.  
Execute scripts **1-SCHEMA.sql**, **2-PRIVILEGES.sql** and **3-ENTRIES.sql** in config/db.  
Schema created: **computer-database-db**
Tables created: **company, computer**  
User created: `admincdb`
with password: `qwerty1234`

## 2. IDE  
### Eclipse  
- Add your project to the current workspace: **File** -> **Import** -> **Existing projects into workspace**    
- Create a new Tomcat 8.0 Server: Follow steps **[HERE](http://www.eclipse.org/webtools/jst/components/ws/M4/tutorials/InstallTomcat.html)**
- In your project properties, select **Project facets**, convert your project to faceted form, and tick **Dynamic Web Module** (3.0) and **Java** (1.8)
- Select **Runtime** tab (in the previous **project facets** menu)  and check the Tomcat 8.0 Server created above as your project runtime  

## 3. Git repository
- Create your own github account, and initialize a new git repository called "computer-database".  
- After the initial commit, add and commit a meaningful .gitignore file. 

You are ready to start coding.

## 4. Start coding
### 4.1. Layout
Your customer requested to build a computer database application. He owns about 500+ computers made by different manufacturers (companies such as Apple, Acer, Asus...).  
Ideally, each computer would contain the following: a name, the date when it was introduced, eventually the date when it was discontinued, and the manufacturer. Obviously, for some reasons, the existing data is incomplete, and he requested that only the name should remain mandatory when adding a computer, the other fields being filled when possible. Furthermore, the date it was discontinued must be greater than the one he was introduced.
The list of computers can be modified, meaning your customer should be able to list, add, delete, and update computers. The list of computers should also be pageable.  
The list of companies should be exhaustive, and therefore will not require any update, deletion etc...  

### 4.2. Command line interface client
The first iteration will be dedicated to implement a first working version of your computer database, with a CLI-UI.  
The CLI will have the following features:

- List computers  
- List companies  
- Show computer details (the detailed information of only one computer)  
- Create a computer  
- Update a computer  
- Delete a computer  

#### 4.2.1. Start
You will organize your project among several packages, such as model, persistence, service, ui, mapper...  
Please use Singleton patterns where it makes sense, and implement your own Persistence management layer (for connections).

#### 4.2.2. Pages
Now that your app's main features work, implement the pageable feature. We recommend the use of a Page class, containing your entities and the page information.  

#### 4.2.3. CODE REVIEW 1, logging (t0 + 3 days)
Important Points: Architecture (daos, mappers, services, models, exceptions etc...)? Singleton, IOC patterns? Validation (dirty checking?)? Date API? Secure inputs?  
Javadoc? Comments? Use Slf4j-api logging library, with the most common implementation: logback.  
