package org.example.controller;

import org.example.service.EntryExitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EntryExitController {
    @Autowired
    EntryExitService entryExitService;
    @PostMapping("/entryGate")
    public ResponseEntity entryIntoStation(@RequestBody String qrCode, @RequestParam String from){
        try {
            boolean permission= entryExitService.isEntryAllow(qrCode,from);
            if(permission){
                return new ResponseEntity("allowed",HttpStatus.OK);
            }else{
                return new ResponseEntity("usedqr",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return  new ResponseEntity("Server issue", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/exitGate")
    public ResponseEntity exitFromStation(@RequestBody String qrCode,@RequestParam String to){
        try {
            boolean permission=entryExitService.isExitAllow(qrCode,to);
            if(permission){
                return new ResponseEntity<>("allowed",HttpStatus.OK);
           }else{
                return new ResponseEntity<>("NOt allowed",HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            return new ResponseEntity("exit not allowed",HttpStatus.BAD_REQUEST);
        }
    }
}
