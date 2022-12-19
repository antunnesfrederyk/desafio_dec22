package br.com.frederykantunnes.challenge;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ActiveProfiles({"test"})
@ExtendWith(MockitoExtension.class)
class ChallengeApplicationTests {

	@Test
	void contextLoads() {
	}

}
