--Hard coded skills
INSERT INTO skill (skill_id, name) VALUES (1, 'Java') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (2, 'Python') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (3, 'JavaScript') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (4, 'C#') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (5, 'React') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (6, 'Angular') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (7, 'Node.js') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (8, 'Spring Boot') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (9, 'Django') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (10, 'SQL') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (11, 'MongoDB') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (12, 'AWS') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (13, 'Docker') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (14, 'Kubernetes') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (15, 'Git') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (16, 'Cybersecurity') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (17, 'HTML') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (18, 'CSS') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (19, 'TypeScript') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (20, 'RESTful API') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (21, 'GraphQL') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (22, 'Jenkins') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (23, 'Redux') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (24, 'Firebase') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (25, 'CI/CD') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (26, 'Kotlin') ON CONFLICT DO NOTHING;
INSERT INTO skill (skill_id, name) VALUES (27, 'C++') ON CONFLICT DO NOTHING;

--Roles
INSERT INTO roles (role_id, name) VALUES (1, 'candidate') ON CONFLICT DO NOTHING;
INSERT INTO roles (role_id, name) VALUES (2, 'manager') ON CONFLICT DO NOTHING;
INSERT INTO roles (role_id, name) VALUES (3, 'admin') ON CONFLICT DO NOTHING;


--Job categories , families
INSERT INTO job_family (id, name) VALUES (1, 'Information Technology') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (2, 'Finance') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (3, 'Human Resources') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (4, 'Healthcare') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (5, 'Marketing') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (6, 'Engineering') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (7, 'Sales') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (8, 'Education') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (9, 'Art and Design') ON CONFLICT DO NOTHING;
INSERT INTO job_family (id, name) VALUES (10, 'Legal') ON CONFLICT DO NOTHING;

--Hard coded user
INSERT INTO users (id,email,first_name,last_name,password) VALUES (1, 'saul@gmail.com','Saul','Olguin','1234') ON CONFLICT DO NOTHING;
