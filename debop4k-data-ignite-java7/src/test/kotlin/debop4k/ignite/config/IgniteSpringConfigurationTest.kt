/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package debop4k.ignite.config

import debop4k.core.loggerOf
import debop4k.core.uninitialized
import org.apache.ignite.Ignite
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.inject.Inject

/**
 * IgniteSpringConfigurationTest
 * @author sunghyouk.bae@gmail.com
 */
@RunWith(SpringRunner::class)
@SpringBootTest(classes = arrayOf(IgniteSpringConfiguration::class))
class IgniteSpringConfigurationTest {

  private val log = loggerOf(javaClass)

  @Inject val ignite: Ignite = uninitialized()

  @Test
  fun testConfiguration() {
    assertThat(ignite).isNotNull()
    log.debug("ignite version={}", ignite.version())
  }
}