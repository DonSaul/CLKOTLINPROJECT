package com.jobsearch.service

import com.jobsearch.dto.SkillDTO
import com.jobsearch.entity.Skill
import com.jobsearch.repository.SkillRepository
import org.springframework.stereotype.Service


@Service
class SkillService (val skillRepository: SkillRepository){


    fun retrieveAllSkills(): List<SkillDTO> {
        val skills = skillRepository.findAll()

        return skills.map {
            SkillDTO(it.skillId!!, it.name)
        }
    }

    fun createSkill(skillDTO: SkillDTO): SkillDTO {
        val skillEntity = skillDTO.let {
            Skill(skillId  =null, name = it.name)
        }
        val newSkill = skillRepository.save(skillEntity)

        return newSkill.let {
            SkillDTO(it.skillId!!, it.name)
        }
    }





}