INSERT INTO TBL_USER(ID,USER_NAME,PASSWORD,EMAIL,ROLE,LOCKED) VALUES(
1001,'Admin','$2y$10$9tsmOp.1KV5XKY8mhNSLherFbWw/P4WKLwGmAKIM9AFyo9hmWMTba','admin@gmail.com','ADMIN'
,'false');
INSERT INTO TBL_USER(ID,USER_NAME,PASSWORD,EMAIL,ROLE,LOCKED) VALUES(
1002,'Vaibhav','$2y$10$9tsmOp.1KV5XKY8mhNSLherFbWw/P4WKLwGmAKIM9AFyo9hmWMTba','vaibhav@gmail.com','STUDENT'
,'false');
INSERT INTO TBL_USER(ID,USER_NAME,PASSWORD,EMAIL,ROLE,LOCKED) VALUES(
1003,'Anshul','$2y$10$9tsmOp.1KV5XKY8mhNSLherFbWw/P4WKLwGmAKIM9AFyo9hmWMTba','anshul@gmail.com','ADMIN'
,'false');
INSERT INTO TBL_USER(ID,USER_NAME,PASSWORD,EMAIL,ROLE,LOCKED) VALUES(
1004,'Maa','$2y$10$9tsmOp.1KV5XKY8mhNSLherFbWw/P4WKLwGmAKIM9AFyo9hmWMTba','maa@gmail.com','ADMIN'
,'false');
INSERT INTO TBL_USER(ID,USER_NAME,PASSWORD,EMAIL,ROLE,LOCKED) VALUES(
1005,'Paa','$2y$10$9tsmOp.1KV5XKY8mhNSLherFbWw/P4WKLwGmAKIM9AFyo9hmWMTba','paa@gmail.com','CONTRIBUTER'
,'false');



INSERT INTO TBL_COURSE(ID,NAME,DESCRIPTION,CATEGORY) VALUES(
1001,'Java with Spring Boot','This course offers a comprehensive journey through Java programming and advanced Spring Boot techniques. Covering core Java essentials, participants delve into object-oriented programming, multithreading, and collections. The course then focuses on Spring Boot for rapid development, RESTful APIs, database integration, security, microservices, and application management. Upon completion, participants confidently architect, develop, and deploy enterprise-level applications using Java and Spring Boot.','programming'
);
INSERT INTO TBL_COURSE(ID,NAME,DESCRIPTION,CATEGORY) VALUES(
1002,'Spring Boot + Angular','This course blends Spring Boot"s backend expertise (RESTful APIs, database integration, security) with Angular"s frontend prowess (dynamic UIs, routing, state management). Participants learn to integrate both technologies for seamless full stack development, gaining hands-on experience in building modern web applications. Ideal for developers seeking proficiency in Full Stack Development, this course equips them to create, deploy, and manage end-to-end applications using Spring Boot and Angular. Upon completion, participants will confidently design and implement full stack solutions aligned with current industry standards.','programming'
);
INSERT INTO TBL_COURSE(ID,NAME,DESCRIPTION,CATEGORY) VALUES(
1003,'Spring Boot Microservices','This course delves into Spring Boot Microservices, covering architecture, RESTful APIs, Spring Cloud tools, and deployment with Docker/Kubernetes. Participants learn microservices principles, communication, fault tolerance, and security. Ideal for Java developers seeking hands-on expertise in building scalable, resilient microservices architectures.','programming'
);

INSERT INTO TBL_COURSE(ID, NAME, DESCRIPTION, CATEGORY) VALUES (
    1004,
    'Data Science Fundamentals',
    'This course provides a comprehensive introduction to Data Science fundamentals, encompassing statistical analysis, machine learning algorithms, data visualization, and practical applications. Participants will gain insights into Python programming, data manipulation, and exploratory data analysis.',
    'data science'
);

INSERT INTO TBL_COURSE(ID, NAME, DESCRIPTION, CATEGORY) VALUES
(1005, 'React.js Fundamentals', 'This course covers the basics of React.js, including components, state management, and JSX. Participants will learn to build interactive web applications using React.js and understand modern front-end development practices.', 'web development'),
(1006, 'Python for Beginners', 'An introductory course to Python programming for beginners. Covers basic syntax, data types, control structures, and simple algorithms. Suitable for individuals new to programming or looking to transition to Python.', 'programming'),
(1007, 'Machine Learning Foundations', 'This course introduces the foundational concepts of machine learning, including supervised and unsupervised learning, regression, classification, and clustering algorithms. Participants will work on practical projects using popular ML libraries.', 'data science'),
(1008, 'Introduction to Blockchain', 'An overview of blockchain technology covering its principles, decentralized networks, smart contracts, and applications beyond cryptocurrencies. Participants will explore real-world use cases and create their blockchain applications.', 'technology'),
(1009, 'Graphic Design Essentials', 'Covers the fundamentals of graphic design, including color theory, typography, layout, and image editing tools. Participants will create visually appealing designs using industry-standard software.', 'design'),
(1010, 'Digital Marketing Strategies', 'This course explores various digital marketing strategies, including SEO, social media marketing, content creation, email campaigns, and analytics. Participants will develop comprehensive digital marketing plans.', 'marketing'),
(1011, 'iOS App Development with Swift', 'A hands-on course on iOS app development using Swift programming language. Covers UI design, data management, networking, and deploying apps to the App Store. Suitable for beginners.', 'mobile development'),
(1012, 'Cybersecurity Fundamentals', 'Introduction to cybersecurity principles, including threat detection, encryption, network security, and ethical hacking techniques. Participants will learn to identify and mitigate common security risks.', 'technology'),
(1013, 'Photography Masterclass', 'A comprehensive guide to photography covering camera settings, composition techniques, lighting, and post-processing. Participants will enhance their photography skills through practical assignments.', 'art'),
(1014, 'Financial Planning & Wealth Management', 'Covers the basics of personal finance, investment strategies, retirement planning, and wealth management. Participants will learn to make informed financial decisions for their future.', 'finance'),
(1015, 'Agile Project Management', 'Introduction to Agile methodologies such as Scrum and Kanban. Covers project planning, iteration management, team collaboration, and delivering high-quality products. Suitable for project managers and teams.', 'business'),
(1016, 'Artificial Intelligence Applications', 'Explores real-world applications of artificial intelligence in industries such as healthcare, finance, gaming, and autonomous systems. Participants will analyze case studies and develop AI-driven solutions.', 'technology');


UPDATE TBL_COURSE SET LINK = 'https://www.youtube.com/watch?v=tljuDMmfJz8';

UPDATE TBL_COURSE SET ACTIVE = true;
