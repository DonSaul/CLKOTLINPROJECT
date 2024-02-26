package com.jobsearch.controller

import com.jobsearch.dto.SkillDTO
import com.jobsearch.service.SkillService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/skills")
class SkillController(val skillService: SkillService) {


    @GetMapping()
    fun retrieveAllSkills(): List<SkillDTO> {
        return skillService.retrieveAllSkills()
    }

    @PostMapping
    fun createSkill(@RequestBody skillDTO: SkillDTO): SkillDTO {
        return skillService.createSkill(skillDTO)
    }

}