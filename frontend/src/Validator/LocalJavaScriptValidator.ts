// src/Validator/LocalJavaScriptValidator.ts

import type { Validator } from './Validator';
import type { Riddle } from '../model/riddle';

export class LocalJavaScriptValidator implements Validator {
    validateTestCase(code: string, riddle: Riddle, testCaseId: number): { status: boolean, message: string } {
        if (riddle && riddle.validationCode && riddle.testCases && code) {
            const testCase = riddle.testCases.find((tc: { id: number }) => tc.id === testCaseId);
            if (testCase) {
                try {
                    const fnBody = `${code}\nreturn (input) => { ${riddle.validationCode}; }`;
                    // eslint-disable-next-line no-new-func
                    const evaluateInput = new Function('input', fnBody)();
                    console.log(fnBody);
                    const userOutput = evaluateInput(testCase.input);
                    const passed = userOutput == testCase.output;
                    if (passed) {
                        return {
                            status: true,
                            message: `Test Case ${testCaseId}: [passed]`
                        };
                    } else {
                        return {
                            status: false,
                            message: `Test Case ${testCaseId}: [failed]\nRiddle Input    : "${String(testCase.input)}"\nUser Output     : "${String(userOutput)}"\nExpected Output : "${String(testCase.output)}"`
                        };
                    }
                } catch (error: any) {
                    return {
                        status: false,
                        message: `Test Case ${testCaseId}: [Execution Error] - ${error instanceof Error ? error.message : String(error)}`
                    };
                }
            }
        }
        return {
            status: false,
            message: `Test Case ${testCaseId}: [failed] - Test case, validation code, or user code missing`
        };
    }
}
