package com.edu.auth.controller;

import com.edu.auth.vo.ResultVO;
import com.edu.auth.dto.UserDTO;
import com.edu.auth.dto.TokenDTO;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.web.bind.annotation.*;


import java.security.Key;
import java.util.Date;


@RestController
public class AuthController {
    private Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
    private long tokenEffDuration = 3600000;

    @GetMapping("/")
    public String test(){
        return "this is auth module!";
    }
    //    Keys.
    @PostMapping("/create_token")
    public String genToken(@RequestBody UserDTO userDTO) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userDTO.getId()).setSubject(userDTO.getId()).setIssuedAt(now).signWith(key).claim("role", userDTO.getRole());
        if (tokenEffDuration >0 ) {
            builder.setExpiration(new Date(nowMillis + tokenEffDuration));
        }
        return builder.compact();
    }

    @PostMapping("/valid_token")
    public ResultVO<String> validToken(@RequestBody TokenDTO tokenDTO) {
        Jws<Claims> jws;
        ResultVO<String> resultVO = new ResultVO<>();
        resultVO.setCode(1);
        resultVO.setMsg("invalid token");
        resultVO.setData("null");
        try {
            jws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(tokenDTO.getToken());
            System.out.println(jws.getBody().getSubject());
            resultVO.setCode(0);
            resultVO.setMsg("valid token");
            resultVO.setData(jws.getBody().getSubject());
        } catch (MissingClaimException mce) {
            // the parsed JWT did not have the sub field
            System.out.println("mce");
        } catch (IncorrectClaimException ice) {
            // the parsed JWT had a sub field, but its value was not equal to 'jsmith'
            System.out.println("ice");
        } catch (Exception e) {

        } finally {

        }
        return resultVO;
    }


}
