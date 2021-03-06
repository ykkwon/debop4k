/*
 * Copyright (c) 2016. Sunghyouk Bae <sunghyouk.bae@gmail.com>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package debop4k.timeperiod.models

import debop4k.timeperiod.utils.halfyearOfMonth
import org.joda.time.DateTime

/**
 * 년/반기 정보를 표현
 * @author debop sunghyouk.bae@gmail.com
 */
data class YearHalfyear(val year: Int, val halfyear: Halfyear) {

  constructor(yh: YearHalfyear) : this(yh.year, yh.halfyear)
  constructor(m: DateTime) : this(m.year, halfyearOfMonth(m.monthOfYear))

}