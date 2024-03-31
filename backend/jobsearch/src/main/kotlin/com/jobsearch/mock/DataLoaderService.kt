package com.jobsearch.mock

import com.jobsearch.dto.*
import com.jobsearch.dto.override.OverrideCvRequestDTO
import com.jobsearch.entity.User
import com.jobsearch.entity.Vacancy
import com.jobsearch.repository.CvRepository
import com.jobsearch.repository.UserRepository
import com.jobsearch.service.JobFamilyService
import com.jobsearch.service.SkillService
import com.jobsearch.service.UserService
import com.jobsearch.service.VacancyService
import com.jobsearch.service.override.OverrideService
import org.springframework.stereotype.Service
import java.time.LocalDate
import kotlin.random.Random

@Service
class DataLoaderService (
    private val jobFamilyService:JobFamilyService,
    private val userRepository:UserRepository,
    private val vacancyService:VacancyService,
    private val overrideService: OverrideService,
    private val cvRepository: CvRepository,
    private val userService: UserService,
    private val skillService: SkillService
) {

    fun createHardVacanciesForManager(manager: UserResponseDTO?) {
        val vacancyDTOs= createVacancyDTOs()
        for (vacancyDto in vacancyDTOs) {
            val selectedJobFamily = jobFamilyService.findByJobFamilyId(vacancyDto.jobFamilyId)
            val managerUser: User? = manager?.let { mgr -> userRepository.findById(mgr.id!!).orElse(null) }
            val vacancyExists = vacancyService.vacancyRepository.existsById(vacancyDto.id!!)
            if (!vacancyExists) {
                val vacancyEntity = vacancyDto.let {
                    Vacancy(
                        it.id,
                        it.name,
                        it.companyName,
                        it.salaryExpectation,
                        it.yearsOfExperience,
                        it.description,
                        selectedJobFamily,
                        managerUser!!
                    )
                }
                vacancyService.vacancyRepository.save(vacancyEntity)
            }
        }
    }

    fun createHardCvForCandidate(candidate: UserResponseDTO?){

        val candidateUser: User? = candidate?.let { can ->
            userRepository.findById(can.id).orElse(null)
        }

        val userHasCV = cvRepository.findFirstByUserOrderByIdDesc(candidateUser!!).orElse(null)

        if (userHasCV==null){

            val jobRequestDTOList = listOf(
                JobRequestDTO(
                    id = 1,
                    startDate = LocalDate.of(2019, 1, 1),
                    endDate = LocalDate.of(2022, 1, 1),
                    company = "Softserve",
                    position = "Software Engineer",
                    description = "Developed web applications using React",
                    jobFamilyId = 1
                ),
                JobRequestDTO(
                    id = 2,
                    startDate = LocalDate.of(2022, 2, 1),
                    endDate = LocalDate.of(2024, 3, 1),
                    company = "Starplus Consultants",
                    position = "Senior Sales Expert",
                    description = "Sold lots of stuff",
                    jobFamilyId = 2
                )

            )
            val projectRequestDTOList = listOf(
                ProjectRequestDTO(
                    id = 1,
                    name = "Job Search Platform",
                    description = "Developed a job search platform",
                    jobFamilyId = 3
                ),
                ProjectRequestDTO(
                    id = 2,
                    name = "Arduino plants",
                    description = "Plants controlled by arduino",
                    jobFamilyId = 4
                )

            )
            val skillIdSet = setOf(1, 2, 3)

            val cvRequest = OverrideCvRequestDTO(
                summary = "Experienced IT professional with extensive expertise in managing technology infrastructures and leading projects to enhance organizational efficiency. Skilled in optimizing systems to meet business objectives.",
                yearsOfExperience = 5,
                salaryExpectation = 70000,
                education = "Bachelor's Degree in Computer Engineering",
                jobs =jobRequestDTOList,
                projects = projectRequestDTOList,
                skillIds = skillIdSet,
                user=candidateUser

            )
            overrideService.createCvOverride(cvRequest)


        }

    }

    fun createRandomCvForCandidateBySeed(candidate: UserResponseDTO?, seed: Int) {
        val candidateUser: User? = candidate?.let { can ->
            userRepository.findById(can.id).orElse(null)
        }

        val userHasCV = cvRepository.findFirstByUserOrderByIdDesc(candidateUser!!).orElse(null)

        if (userHasCV == null) {

            val random = Random(seed)
            val jobCompanies = listOf(
                "Google",
                "Microsoft",
                "Apple",
                "Amazon",
                "Facebook",
                "Netflix",
                "Tesla",
                "Airbnb",
                "Uber"
            )


            val jobFamilies = mapOf(
                1 to listOf("Software Engineer", "Web Developer", "Data Scientist"),
                2 to listOf("Financial Analyst", "Accountant", "Investment Banker"),
                3 to listOf("HR Manager", "Recruiter", "Training Coordinator"),
                4 to listOf("Nurse", "Physician Assistant", "Medical Technologist"),
                5 to listOf("Marketing Manager", "Digital Marketer", "Brand Ambassador"),
                6 to listOf("Civil Engineer", "Electrical Engineer", "Mechanical Engineer"),
                7 to listOf("Sales Representative", "Account Executive", "Sales Manager"),
                8 to listOf("Teacher", "Professor", "Educational Administrator"),
                9 to listOf("Graphic Designer", "UI/UX Designer", "Animator"),
                10 to listOf("Lawyer", "Legal Assistant", "Paralegal")
            )

            val positionDescriptions = mapOf(
                "Software Engineer" to "Developed web applications using React.",
                "Web Developer" to "Designed and implemented responsive web applications.",
                "Data Scientist" to "Analyzed large datasets using machine learning algorithms.",
                "Financial Analyst" to "Performed financial analysis and forecasting.",
                "Accountant" to "Managed financial records and prepared financial statements.",
                "Investment Banker" to "Advised clients on financial transactions and investments.",
                "HR Manager" to "Managed employee recruitment, onboarding, and performance evaluation.",
                "Recruiter" to "Sourced and screened candidates for job openings.",
                "Training Coordinator" to "Developed and conducted employee training programs.",
                "Nurse" to "Provided direct patient care and assisted with medical procedures.",
                "Physician Assistant" to "Assisted physicians in diagnosing and treating patients.",
                "Medical Technologist" to "Performed laboratory tests and analyzed results.",
                "Marketing Manager" to "Developed and executed marketing strategies to drive sales.",
                "Digital Marketer" to "Implemented digital marketing campaigns across various platforms.",
                "Brand Ambassador" to "Promoted brand awareness through events and social media.",
                "Civil Engineer" to "Designed and supervised construction projects.",
                "Electrical Engineer" to "Developed electrical systems for buildings and infrastructure.",
                "Mechanical Engineer" to "Designed and optimized mechanical systems and components.",
                "Sales Manager" to "Led sales team to achieve revenue targets and expand market share.",
                "Account Executive" to "Managed client accounts and cultivated business relationships.",
                "Teacher" to "Planned and delivered instructional lessons to students.",
                "Professor" to "Conducted research and taught courses at the university level.",
                "Educational Administrator" to "Managed school operations and implemented educational policies.",
                "Graphic Designer" to "Created visual concepts and designs for print and digital media.",
                "UI/UX Designer" to "Designed user interfaces and experiences for digital products.",
                "Animator" to "Produced animated sequences for films, games, and other media.",
                "Lawyer" to "Provided legal counsel and representation to clients.",
                "Legal Assistant" to "Assisted attorneys with legal research and document preparation.",
                "Paralegal" to "Performed administrative tasks to support legal proceedings."
            )

            val jobRequestDTOList = mutableListOf<JobRequestDTO>()
            repeat(random.nextInt(1, 3)) {
                val randomJobFamilyId = random.nextInt(1, 11)
                val jobNames = jobFamilies[randomJobFamilyId] ?: emptyList()
                val position = jobNames.random(random)
                val description = positionDescriptions[position] ?: "No description for this job"
                val startDate = LocalDate.of(random.nextInt(2015, 2020), random.nextInt(1, 13), random.nextInt(1, 29))
                val endDate = LocalDate.of(random.nextInt(2020, 2024), random.nextInt(1, 3), random.nextInt(1, 29))
                jobRequestDTOList.add(
                    JobRequestDTO(
                        id = it + 1,
                        startDate = startDate,
                        endDate = endDate,
                        company = jobCompanies.random(random),
                        position = position,
                        description = description,
                        jobFamilyId = randomJobFamilyId
                    )
                )
            }

            val yearsOfExperienceFromJobs = jobRequestDTOList.sumOf { it.endDate.year - it.startDate.year }

            val projectFamilies = mapOf(
                1 to listOf("Web Application", "Mobile App", "Data Analysis Tool"),
                2 to listOf("Financial Management System", "Budgeting Software", "Investment Portfolio Tracker"),
                3 to listOf("HR Management System", "Recruitment Platform", "Employee Training Portal"),
                4 to listOf("Medical Records System", "Patient Care App", "Healthcare Analytics Platform"),
                5 to listOf("Marketing Campaign Manager", "Social Media Analytics Tool", "Brand Management System"),
                6 to listOf("Infrastructure Development Project", "Electrical Grid Enhancement", "Mechanical Systems Upgrade"),
                7 to listOf("Sales CRM", "Account Management Software", "Sales Performance Dashboard"),
                8 to listOf("Education Management System", "Online Course Platform", "Student Information System"),
                9 to listOf("Graphic Design Project", "UI/UX Redesign", "Animation Production"),
                10 to listOf("Legal Case Management System", "Law Firm Website Development", "Paralegal Training Program")
            )

            val projectDescriptions = mapOf(
                "Web Application" to "Developed a web application using modern frontend and backend technologies.",
                "Mobile App" to "Built a mobile application for Android and iOS platforms with intuitive user interfaces.",
                "Data Analysis Tool" to "Implemented data visualization and statistical analysis tools for business insights.",
                "Financial Management System" to "Designed a comprehensive financial management system for budgeting and forecasting.",
                "Budgeting Software" to "Developed software for budget creation, tracking, and financial planning.",
                "Investment Portfolio Tracker" to "Created a platform to monitor and analyze investment portfolios.",
                "HR Management System" to "Built a system to streamline HR processes including recruitment, onboarding, and performance management.",
                "Recruitment Platform" to "Developed an online platform for job posting, applicant tracking, and candidate evaluation.",
                "Employee Training Portal" to "Designed a digital platform for delivering employee training programs and resources.",
                "Medical Records System" to "Developed a secure system for storing and accessing patient medical records.",
                "Patient Care App" to "Built a mobile application to facilitate communication between patients and healthcare providers.",
                "Healthcare Analytics Platform" to "Created a platform for analyzing healthcare data to improve patient outcomes and operational efficiency.",
                "Marketing Campaign Manager" to "Developed a tool for planning, executing, and analyzing marketing campaigns.",
                "Social Media Analytics Tool" to "Analyzed social media data to identify trends, monitor brand sentiment, and measure campaign effectiveness.",
                "Brand Management System" to "Created a centralized system for managing brand assets, guidelines, and communications.",
                "Infrastructure Development Project" to "Led a project to design and implement infrastructure improvements.",
                "Electrical Grid Enhancement" to "Developed solutions to enhance the reliability and efficiency of electrical grids.",
                "Mechanical Systems Upgrade" to "Upgraded mechanical systems to improve performance and reduce maintenance costs.",
                "Sales CRM" to "Implemented a customer relationship management system to track leads, opportunities, and customer interactions.",
                "Account Management Software" to "Built software to manage client accounts, contracts, and billing.",
                "Sales Performance Dashboard" to "Created a dashboard to visualize sales data and track key performance metrics.",
                "Education Management System" to "Developed a system for managing student records, course schedules, and academic programs.",
                "Online Course Platform" to "Designed a platform for delivering online courses, lectures, and educational content.",
                "Student Information System" to "Built a system to track student enrollment, attendance, and academic progress.",
                "Graphic Design Project" to "Created visual designs for branding, marketing materials, and digital media.",
                "UI/UX Redesign" to "Redesigned user interfaces for improved usability, accessibility, and user experience.",
                "Animation Production" to "Produced animated content for advertisements, videos, and multimedia presentations.",
                "Legal Case Management System" to "Developed a system to manage legal cases, documents, and client communications.",
                "Law Firm Website Development" to "Designed and developed websites for law firms with client portals and informational content.",
                "Paralegal Training Program" to "Created a training program for paralegals covering legal procedures, research methods, and case management."
            )




            val projectRequestDTOList= mutableListOf<ProjectRequestDTO>()
            repeat(random.nextInt(1, 3)){
                val randomJobFamilyId = random.nextInt(1, 11)
                val family = projectFamilies[randomJobFamilyId]?: emptyList()
                val projectName = family.random(random)
                val description = projectDescriptions[projectName] ?: "No description for this project"

                projectRequestDTOList.add(
                    ProjectRequestDTO(
                        id = it+1,
                        name = projectName,
                        description = description,
                        jobFamilyId = randomJobFamilyId
                    ),
                )

            }



            val allSkills = skillService.retrieveAllSkills()
            val skillIdSet = mutableSetOf<Int>()
            val availableSkills = allSkills.map { it.skillId }.filterNot { it in skillIdSet }.toMutableList()

            repeat(random.nextInt(1, 5)) {
                val randomSkillId = availableSkills[availableSkills.random(random)-1]
                skillIdSet.add(randomSkillId)

                availableSkills.remove(randomSkillId)
            }


            val educations = listOf(
                "Computer Science",
                "Engineering",
                "Business Administration",
                "Psychology",
                "Marketing",
                "Finance",
                "Biology",
                "Chemistry",
                "Physics",
                "Mathematics",
                "English Literature",
                "History",
                "Sociology",
                "Art",
                "Political Science"
            )

            val summaries = listOf(
                "Experienced IT professional with extensive expertise in managing technology infrastructures and leading projects to enhance organizational efficiency. Skilled in optimizing systems to meet business objectives.",
                "Results-oriented software engineer with a passion for developing innovative web applications using cutting-edge technologies. Proven track record of delivering high-quality software solutions on time and within budget.",
                "Dynamic project manager with a proven ability to lead cross-functional teams and drive project success. Strong background in agile methodologies and project management best practices.",
                "Detail-oriented data analyst with a knack for extracting actionable insights from complex datasets. Proficient in data visualization and statistical analysis tools.",
                "Motivated sales representative with a demonstrated history of exceeding sales targets and driving revenue growth. Strong communication and negotiation skills.",
                "Creative marketing specialist with a passion for developing and executing impactful marketing campaigns. Proficient in digital marketing strategies and social media management.",
                "Passionate educator with a commitment to fostering a positive learning environment. Experienced in curriculum development and student-centered teaching methods.",
                "Dedicated healthcare professional with a focus on providing high-quality patient care and promoting wellness. Skilled in clinical assessments and treatment planning.",
                "Innovative product manager with a proven track record of bringing successful products to market. Experienced in product lifecycle management and market analysis.",
                "Detail-oriented accountant with expertise in financial analysis and reporting. Proficient in accounting software and regulatory compliance.",
                "Creative graphic designer with a passion for visual storytelling. Experienced in print and digital design, with a focus on brand identity and user experience.",
                "Versatile administrative assistant with strong organizational skills and attention to detail. Experienced in managing calendars, scheduling appointments, and coordinating meetings.",
                "Motivated customer service representative with a passion for delivering exceptional service experiences. Skilled in problem-solving and conflict resolution.",
                "Dynamic human resources professional with expertise in recruitment, employee relations, and performance management. Experienced in developing HR policies and procedures.",
                "Analytical research scientist with a focus on exploring new technologies and solving complex scientific problems. Experienced in experimental design and data analysis.",
                "Creative content writer with a talent for crafting compelling stories and engaging copy. Proficient in SEO and content marketing strategies."
            )


            val cvRequest = OverrideCvRequestDTO(
                summary = summaries.random(random),
                yearsOfExperience = yearsOfExperienceFromJobs,
                salaryExpectation = random.nextInt(50000, 100000),
                education = educations.random(random),
                jobs = jobRequestDTOList,
                projects = projectRequestDTOList,
                skillIds = skillIdSet,
                user = candidateUser
            )

            overrideService.createCvOverride(cvRequest)
        }
    }



    fun createHardCandidates():List<UserResponseDTO> {

        val createdUsers = mutableListOf<UserResponseDTO>()
        val candidateList= createCandidateDTOs()
        candidateList.forEach{

            val createdUser=userService.createUser(it)
            createdUsers.add(createdUser!!)
        }

        return createdUsers


    }

    fun createHardManagers(){

        val managerList = createManagerDTOs()
        managerList.forEach {
            userService.createUser(it)
        }

    }



    fun createManagerDTOs():List<UserRequestDTO>{
        val userRequestDTOList = listOf(
            UserRequestDTO(
                firstName = "Rodrigo",
                lastName = "Johnson",
                email = "rodrigo@example.com",
                password = "a123",
                roleId = 2
            ),
            UserRequestDTO(
                firstName = "Esteban",
                lastName = "Smith",
                email = "esteban@example.com",
                password = "a123",
                roleId = 2
            ),
            UserRequestDTO(
                firstName = "Michelle",
                lastName = "Williams",
                email = "michelle@example.com",
                password = "a123",
                roleId = 2
            ),

        )

        return userRequestDTOList


    }




    fun createCandidateDTOs():List<UserRequestDTO> {
        val userRequestDTOList = listOf(
        UserRequestDTO(
            firstName = "Branden",
            lastName = "Banks",
            email = "branden@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Nathalia",
            lastName = "Norton",
            email = "nathalia@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Alice",
            lastName = "Smith",
            email = "alice@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Bob",
            lastName = "Johnson",
            email = "bob@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Kendal",
            lastName = "Walls",
            email = "kendal@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Jadon",
            lastName = "Conway",
            email = "jadon@example.com",
            password = "a123",
            roleId = 1
        ),
        UserRequestDTO(
            firstName = "Gianna",
            lastName = "Chandler",
            email = "gianna@example.com",
            password = "a123",
            roleId = 1
        ),

    )
    return userRequestDTOList
}


    fun createVacancyDTOs(): List<VacancyRequestDTO> {
        return listOf(
            VacancyRequestDTO(
                id = 1,
                name = "Software Engineer",
                companyName = "Google",
                salaryExpectation = 50000,
                yearsOfExperience = 2,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 1
            ),
            VacancyRequestDTO(
                id = 2,
                name = "Software Engineer Junior",
                companyName = "Google",
                salaryExpectation = 60000,
                yearsOfExperience = 1,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 1
            ),
            VacancyRequestDTO(
                id = 3,
                name = "Software Engineer Senior",
                companyName = "Microsoft",
                salaryExpectation = 99999,
                yearsOfExperience = 1,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 1
            ),
            VacancyRequestDTO(
                id = 4,
                name = "DevOps Engineer",
                companyName = "Microsoft",
                salaryExpectation = 66666,
                yearsOfExperience = 4,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 1
            ),
            VacancyRequestDTO(
                id = 5,
                name = "Tax Consultant",
                companyName = "Amazon",
                salaryExpectation = 123123,
                yearsOfExperience = 10,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 2
            ),
            VacancyRequestDTO(
                id = 5,
                name = "Financial Planner",
                companyName = "Netflix",
                salaryExpectation = 121212,
                yearsOfExperience = 10,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 2
            ),
            VacancyRequestDTO(
                id = 6,
                name = "Physician",
                companyName = "HealthInc",
                salaryExpectation = 232323,
                yearsOfExperience = 10,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 4
            ),
            VacancyRequestDTO(
                id = 7,
                name = "Paralegal",
                companyName = "LawIntegrity",
                salaryExpectation = 232323,
                yearsOfExperience = 10,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 10
            ),
            VacancyRequestDTO(
                id = 7,
                name = "Legal consultant",
                companyName = "LawIntegrity",
                salaryExpectation = 1111,
                yearsOfExperience = 1,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 10
            ),
            VacancyRequestDTO(
                id = 8,
                name = "AI Engineer",
                companyName = "Tesla",
                salaryExpectation = 1111,
                yearsOfExperience = 1,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 1
            ),
            VacancyRequestDTO(
                id = 9,
                name = "Mechanical Engineer Master",
                companyName = "Tesla",
                salaryExpectation = 456456,
                yearsOfExperience = 20,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 6
            ),
            VacancyRequestDTO(
                id = 10,
                name = "CHIP Architect Engineer",
                companyName = "NVIDIA",
                salaryExpectation = 898989,
                yearsOfExperience = 25,
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
                jobFamilyId = 6
            ),
        )
    }
}
