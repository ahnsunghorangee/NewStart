export const pureSort = <T>(array: readonly T[]): T[] => { // readonly: 원본 배열 유지 후
    let deepCopied = [...array] // 깊은 복사
    return deepCopied.sort()
}