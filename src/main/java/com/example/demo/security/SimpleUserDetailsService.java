package com.example.demo.security;

import com.example.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * User�G���e�B�e�B
 */
@Service("simpleUserDetailsService")
public class SimpleUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    public SimpleUserDetailsService(UserRepository userRepository) 
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) 
    {
        // email�Ńf�[�^�x�[�X���烆�[�U�[�G���e�B�e�B����������
        return userRepository.findByEmail(email)
			                .map(SimpleLoginUser::new)
			                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }
}
