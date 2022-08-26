/*
 * Copyright 2022 Confluent Inc.
 *
 * Licensed under the Confluent Community License; you may not use this file
 * except in compliance with the License.  You may obtain a copy of the License at
 *
 * http://www.confluent.io/confluent-community-license
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OF ANY KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.confluent.ksql.function.udf.math;

import io.confluent.ksql.function.FunctionCategory;
import io.confluent.ksql.function.udf.Udf;
import io.confluent.ksql.function.udf.UdfDescription;
import io.confluent.ksql.function.udf.UdfParameter;
import io.confluent.ksql.util.KsqlConstants;

@UdfDescription(
        name = "gcd",
        category = FunctionCategory.MATHEMATICAL,
        description = Gcd.DESCRIPTION,
        author = KsqlConstants.CONFLUENT_AUTHOR
)
public class Gcd {

  static final String DESCRIPTION = "Returns the greatest common divisor of the two inputs.";


  @Udf
  public Integer gcd(@UdfParameter final Integer a, @UdfParameter final Integer b) {

    /* The GCD can never be larger than either input, so converting the result to an integer
       will not cause overflow. */
    final Long result = gcd(a == null ? null : a.longValue(), b == null ? null : b.longValue());

    return result == null ? null : result.intValue();
  }

  @Udf
  public Long gcd(@UdfParameter final Long a, @UdfParameter final Long b) {
    if (a == null || b == null) {
      return null;
    }

    if (a == 0 && b == 0) {
      return 0L;
    }

    long currentA = Math.abs(a);
    long currentB = Math.abs(b);

    // Calculate GCD using Euclid's efficient algorithm
    while (currentB != 0) {
      final long oldB = currentB;
      currentB = currentA % oldB;
      currentA = oldB;
    }

    return currentA;
  }

}
