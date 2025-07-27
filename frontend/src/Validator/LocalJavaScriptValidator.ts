// src/Validator/LocalJavaScriptValidator.ts

import type { Validator } from './Validator';
import type { Riddle } from '../model/models';

export class LocalJavaScriptValidator implements Validator {
    validateTestCase(code: string, riddle: Riddle, testCaseIdx: number): { status: boolean, message: string } {
        if (riddle && riddle.validationCode && riddle.testCases && code) {
            const testCase = riddle.testCases[testCaseIdx];
            if (testCase) {
                try {
                    const fnBody = `${code}\nreturn (input) => { ${riddle.validationCode}; }`;
                    // eslint-disable-next-line no-new-func
                    const evaluateInput = new Function('input', fnBody)();
                    const userOutput = evaluateInput(testCase.input);
                    const passed = userOutput == testCase.output;
                    if (passed) {
                        return {
                            status: true,
                            message: `Test Case ${testCaseIdx + 1}: [passed]`
                        };
                    } else {
                        return {
                            status: false,
                            message: `Test Case ${testCaseIdx + 1}: [failed]\nRiddle Input    : "${String(testCase.input)}"\nUser Output     : "${String(userOutput)}"\nExpected Output : "${String(testCase.output)}"`
                        };
                    }
                } catch (error: any) {
                    return {
                        status: false,
                        message: `Test Case ${testCaseIdx + 1}: [Execution Error] - ${error instanceof Error ? error.message : String(error)}`
                    };
                }
            }
        }
        return {
            status: false,
            message: `Test Case ${testCaseIdx + 1}: [failed] - Test case, validation code, or user code missing`
        };
    }
}
