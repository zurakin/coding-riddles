// src/Validator/Validator.ts

import type { Riddle } from '../model/riddle';

export interface Validator {
    validateTestCase(
        code: string,
        riddle: Riddle,
        testCaseId: number
    ): { status: boolean, message: string };
}
