package com.jobsearch.controller

import com.jobsearch.service.GeneratePdfService
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/v1/pdf")
class FileTransferController(private val generatePdfService: GeneratePdfService) {

    @GetMapping("/{userId}" ,produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getPdf(@PathVariable userId: Int): ResponseEntity<ByteArray> {
        val pdfBytes = generatePdfService.getUserCv(userId)

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }

    @GetMapping(produces = [MediaType.APPLICATION_PDF_VALUE])
    fun getPdf(): ResponseEntity<ByteArray> {
        val pdfBytes = generatePdfService.getAuthUserCv()

        return ResponseEntity.ok()
            .contentType(MediaType.APPLICATION_PDF)
            .body(pdfBytes)
    }
}